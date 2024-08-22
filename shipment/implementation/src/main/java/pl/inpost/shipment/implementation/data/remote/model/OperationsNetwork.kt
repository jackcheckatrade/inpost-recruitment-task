package pl.inpost.shipment.implementation.data.remote.model

import pl.inpost.shipment.api.model.Operations

/**
 * @param manualArchive - shipment can be manually (gesture) archived
 * @param delete - shipment can be manually deleted
 * @param collect - shipment can be remotely collected
 * @param highlight - shipment is ready to pick up - grouping
 * @param expandAvizo - shipment time to pick up can be expanded - show button
 * @param endOfWeekCollection - shipment will be available to pick up over the weekend - change colors
 */
data class OperationsNetwork(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
) {
    constructor(operations: Operations) : this(
        operations.manualArchive,
        operations.delete,
        operations.collect,
        operations.highlight,
        operations.expandAvizo,
        operations.endOfWeekCollection
    )

    fun toDomain(): Operations = Operations(
        manualArchive = manualArchive,
        delete = delete,
        collect = collect,
        highlight = highlight,
        expandAvizo = expandAvizo,
        endOfWeekCollection = endOfWeekCollection
    )
}
