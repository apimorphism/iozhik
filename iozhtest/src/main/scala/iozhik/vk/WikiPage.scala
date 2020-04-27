package iozhik.vk

final case class WikiPage(id: Int,
                          groupId: Int,
                          creatorId: Int,
                          title: String,
                          currentUserCanEdit: Boolean,
                          currentUserCanEditAccess: Boolean,
                          whoCanView: Int,
                          whoCanEdit: Int,
                          edited: Int,
                          created: Int,
                          editorId: Int,
                          views: Int,
                          parent: String,
                          parent2: String,
                          source: String,
                          html: String,
                          viewUrl: String)
