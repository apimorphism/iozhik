package iozhik.vk

final case class Discussion(id: Int,
                            title: String,
                            created: Int,
                            createdBy: Int,
                            updated: Int,
                            updateBy: Int,
                            isClosed: Boolean,
                            isFixed: Boolean,
                            comments: Int,
                            firstComment: String,
                            lastComment: String)
