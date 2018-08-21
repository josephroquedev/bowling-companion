package ca.josephroque.bowlingcompanion.statistics.impl

import android.os.Parcel
import android.os.Parcelable
import ca.josephroque.bowlingcompanion.R
import ca.josephroque.bowlingcompanion.common.interfaces.parcelableCreator
import ca.josephroque.bowlingcompanion.games.lane.*

/**
 * Copyright (C) 2018 Joseph Roque
 *
 * Percentage of possible left splits which the user successfully spared.
 */
class LeftSplitsSparedStatistic(numerator: Int, denominator: Int) : SecondBallStatistic(numerator, denominator) {

    // MARK: Modifiers

    /** @Override */
    override fun isModifiedByFirstBall(deck: Deck) = deck.isLeftSplit

    /** @Override */
    override fun isModifiedBySecondBall(deck: Deck) = deck.arePinsCleared()

    // MARK: Overrides

    override val titleId = Id
    override val id = Id.toLong()

    // MARK: Parcelable

    companion object {
        /** Creator, required by [Parcelable]. */
        @Suppress("unused")
        @JvmField val CREATOR = parcelableCreator(::LeftSplitsSparedStatistic)

        /** Unique ID for the statistic. */
        const val Id = R.string.statistic_left_splits_spared
    }

    /**
     * Construct this statistic from a [Parcel].
     */
    constructor(p: Parcel): this(numerator = p.readInt(), denominator = p.readInt())
}