package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.dao.ArticleTemplateDao
import com.zavijavasoft.yafina.model.ArticleTemplateEntity
import com.zavijavasoft.yafina.model.ArticleTemplateStorage
import javax.inject.Inject

class ArticleTemplateStorageImpl @Inject constructor(
        private val dao: ArticleTemplateDao

) : ArticleTemplateStorage {
    override fun updateTemplate(articleTemplate: ArticleTemplateEntity) {
        dao.updateTemplate(articleTemplate)
    }

    override fun getTemplateByArticleId(articleId: Long): ArticleTemplateEntity {
        return dao.getTemplateByArticleId(articleId)
    }

    override fun insertTemplate(articleTemplate: ArticleTemplateEntity) {
        dao.insertTemplate(articleTemplate)
    }

    override fun deleteTemplate(articleTemplate: ArticleTemplateEntity) {
        dao.deleteTemplate(articleTemplate)
    }
}