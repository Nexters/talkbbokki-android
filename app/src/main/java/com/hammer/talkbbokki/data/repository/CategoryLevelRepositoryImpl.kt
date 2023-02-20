package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.repository.CategoryLevelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CategoryLevelRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : CategoryLevelRepository {

    private val localCategory = listOf<CategoryLevel>(
        CategoryLevel(
            id = "level1",
            title = "아직 인사만\n하는 사이",
            bgColor = "#9C5FFF",
            image = R.drawable.image_category_01,
            isActive = true
        ),
        CategoryLevel(
            id = "level2",
            title = "우리 점심\n먹는 사이",
            bgColor = "#1EAC90",
            image = R.drawable.image_category_02,
            isActive = true
        ),
        CategoryLevel(
            id = "level3",
            title = "이젠 술 한잔\n하는 사이",
            bgColor = "#FBB21E",
            image = R.drawable.image_category_03,
            isActive = true
        ),
        CategoryLevel(
            id = "event",
            title = "COMING\nSOON",
            bgColor = "#555555",
            image = R.drawable.image_category_coming_soon,
            isActive = false
        )
    )

    override fun getCategoryLevel(): Flow<List<CategoryLevel>> = flow {
        emit(
            localCategory
//            service.getCategoryLevel().result.map { it.toModel() }
        )
    }
}
