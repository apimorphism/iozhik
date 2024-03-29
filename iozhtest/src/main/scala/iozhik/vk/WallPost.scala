package iozhik.vk

final case class WallPost(
  id: Int,
  ownerId: Int,
  fromId: Int,
  createdBy: Int,
  date: Long,
  text: String,
  replyOwnerId: Int,
  replyPostId: Int,
  comments: WallPostCommentsAnon,
  likes: WallPostLikesAnon,
  reposts: WallPostReportsAnon,
  views: WallPostViewsAnon,
  postType: PostType,
  postSource: PostSource,
  attachments: Vector[Attachment],
  geo: Geo,
  signerId: Int,
  copyHistory: Vector[WallPost],
  canPin: Boolean,
  canDelete: Boolean,
  canEdit: Boolean,
  isPinned: Boolean,
  markedAsAds: Boolean,
  isFavorite: Boolean,
  friendsOnly: Option[Int] = Option.empty
)
