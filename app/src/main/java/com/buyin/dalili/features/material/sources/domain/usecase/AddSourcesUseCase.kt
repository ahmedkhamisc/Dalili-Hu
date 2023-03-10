package com.buyin.dalili.features.material.sources.domain.usecase

import com.buyin.dalili.features.material.sources.data.SourcesRepository
import com.buyin.dalili.features.material.sources.domain.model.SourcesModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AddSourcesUseCase @Inject constructor(
    private val repository: SourcesRepository
) {

    operator fun invoke(model: SourcesModel) {
        return repository.addSources(model)
    }

}
