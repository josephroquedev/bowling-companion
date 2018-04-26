package ca.josephroque.bowlingcompanion.series

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import ca.josephroque.bowlingcompanion.App
import ca.josephroque.bowlingcompanion.R
import ca.josephroque.bowlingcompanion.common.Android
import ca.josephroque.bowlingcompanion.common.fragments.DatePickerFragment
import ca.josephroque.bowlingcompanion.utils.Color
import ca.josephroque.bowlingcompanion.utils.DateUtils
import ca.josephroque.bowlingcompanion.utils.safeLet
import kotlinx.android.synthetic.main.dialog_series.*
import kotlinx.android.synthetic.main.dialog_series.view.*
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 * Copyright (C) 2018 Joseph Roque
 *
 * Dialog to edit a series.
 */
class SeriesDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        /** Logging identifier. */
        private const val TAG = "SeriesDialog"

        /** Identifier for the [Series] to be edited. */
        private const val ARG_SERIES = "${TAG}_series"

        /** Identifier for the new date of the series. */
        private const val ARG_DATE = "${TAG}_date"

        /**
         * Create a new instance of the [SeriesDialog].
         *
         * @param series the series to edit
         */
        fun newInstance(series: Series): SeriesDialog {
            val dialog = SeriesDialog()
            val args = Bundle()
            args.putParcelable(ARG_SERIES, series)
            dialog.arguments = args
            return dialog
        }
    }

    /** Series to edit. */
    private var series: Series? = null

    /** Updated date for series. */
    private var currentDate: Date? = null

    /** Interaction handler. */
    private var listener: OnSeriesDialogInteractionListener? = null

    /** View OnClickListener. */
    private var onClickListener: View.OnClickListener? = View.OnClickListener {
        val clicked = it ?: return@OnClickListener
        when (clicked.id) {
            R.id.btn_change_date -> {
                showChangeDateDialog()
            }
            R.id.btn_delete -> {
                safeLet(context, series) { context, series ->
                    AlertDialog.Builder(context)
                            .setTitle(String.format(context.resources.getString(R.string.query_delete_item), series.prettyDate))
                            .setMessage(R.string.dialog_delete_item_message)
                            .setPositiveButton(R.string.delete, { _, _ ->
                                listener?.onDeleteSeries(series)
                                dismiss()
                            })
                            .setNegativeButton(R.string.cancel, null)
                            .show()
                }
            }
        }
    }

    /** @Override */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        series = savedInstanceState?.getParcelable(ARG_SERIES) ?: arguments?.getParcelable(ARG_SERIES)
        currentDate = Date(savedInstanceState?.getLong(ARG_DATE) ?: series?.date?.time ?: 0)

        val rootView = inflater.inflate(R.layout.dialog_series, container, false)

        setupToolbar(rootView)
        return rootView
    }

    /**
     * Set up title, style, and listeners for toolbar.
     *
     * @param rootView the root view
     */
    private fun setupToolbar(rootView: View) {
        rootView.toolbar_series.setTitle(R.string.edit_series)

        rootView.btn_change_date.setOnClickListener(onClickListener)
        rootView.btn_delete.setOnClickListener(onClickListener)
        rootView.toolbar_series.apply {
            inflateMenu(R.menu.menu_dialog_series)
            setNavigationIcon(R.drawable.ic_close_white_24dp)
            setNavigationOnClickListener {
                dismiss()
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_save -> {
                        saveSeries()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    /** @Override */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context as? OnSeriesDialogInteractionListener ?: throw RuntimeException(context!!.toString() + " must implement OnSeriesDialogInteractionListener")
        listener = context
    }

    /** @Override */
    override fun onDetach() {
        super.onDetach()
        listener = null
        onClickListener = null
    }

    /** @Override */
    override fun onResume() {
        super.onResume()
        currentDate?.let { tv_date.text = DateUtils.dateToPretty(it) }
        updateSaveButton()
    }

    /** @Override */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_SERIES, series)
    }

    /** @Override */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    /**
     * Clean up dialog before calling super.
     */
    override fun dismiss() {
        App.hideSoftKeyBoard(activity!!)
        activity?.supportFragmentManager?.popBackStack()
        super.dismiss()
    }

    /**
     * Update save button state based on if the series can be saved or not.
     */
    private fun updateSaveButton() {
        val saveButton = toolbar_series?.menu?.findItem(R.id.action_save)
        saveButton?.isEnabled = true
        saveButton?.icon?.alpha = Color.ALPHA_ENABLED
    }

    /**
     * Save the current series. Show errors if there are any.
     */
    private fun saveSeries() {
        launch(Android) {
            val context = this@SeriesDialog.context ?: return@launch
            val series = this@SeriesDialog.series ?: return@launch
            val currentDate = this@SeriesDialog.currentDate ?: return@launch

            val oldDate = series.date
            series.date = currentDate

            val error = series.save(context).await()
            if (error != null) {
                error.show(context)
                series.date = oldDate
                tv_date.text = series.prettyDate
            } else {
                dismiss()
                listener?.onFinishSeries(series)
            }
        }
    }

    /**
     * Show dialog to let user change series date.
     */
    private fun showChangeDateDialog() {
        val supportFragmentManager = activity?.supportFragmentManager ?: return

        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        val dialog = DatePickerFragment.newInstance(calendar)
        dialog.listener = this

        dialog.show(supportFragmentManager, "DatePickerFragment")
    }

    /** @Override */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        currentDate = calendar.time
        tv_date.text = DateUtils.dateToPretty(currentDate!!)
    }

    /**
     * Handles interaction with the dialog.
     */
    interface OnSeriesDialogInteractionListener {

        /**
         * Indicates when the user has finished editing the [Series].
         *
         * @param series the finished [Series]
         */
        fun onFinishSeries(series: Series)

        /**
         * Indicates the user wishes to delete the [Series].
         *
         * @param series the deleted [Series]
         */
        fun onDeleteSeries(series: Series)
    }

}