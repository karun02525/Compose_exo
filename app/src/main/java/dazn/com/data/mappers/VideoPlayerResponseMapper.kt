package dazn.com.data.mappers

import dazn.com.domain.model.PlayListModel
import dazn.com.network.common.Mapper
import dazn.com.network.entity.product.ApiVideoItem
import javax.inject.Inject


class VideoPlayerResponseMapper @Inject constructor() : Mapper<ApiVideoItem, PlayListModel> {

    override fun toDomain(from: ApiVideoItem): PlayListModel {
        return PlayListModel(
            name = from.name,
            uri = from.uri,

        )
    }
}
