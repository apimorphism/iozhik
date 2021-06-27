package iozhik.vk

sealed trait Event {
  def groupId: Int
}

final case class EventMessageNew(groupId: Int, `object`: Message) extends Event

final case class EventMessageReply(groupId: Int, `object`: Message) extends Event

final case class EventMessageEdit(groupId: Int, `object`: Message) extends Event

final case class EventMessageAllow(groupId: Int, userId: Int, key: String) extends Event

final case class EventMessageDeny(groupId: Int, userId: Int) extends Event

final case class EventPhotoNew(groupId: Int, `object`: Photo) extends Event

final case class EventPhotoCommentNew(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  photoId: Int,
  photoOwnerId: Int
) extends Event

final case class EventPhotoCommentEdit(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  photoId: Int,
  photoOwnerId: Int
) extends Event

final case class EventPhotoCommentRestore(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  photoId: Int,
  photoOwnerId: Int
) extends Event

final case class EventPhotoCommentDelete(groupId: Int, ownerId: Int, id: Int, userId: Int, deleterId: Int, photoId: Int)
    extends Event

final case class EventAudioNew(groupId: Int, `object`: Audio) extends Event

final case class EventVideoNew(groupId: Int, `object`: Video) extends Event

final case class EventVideoCommentNew(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  videoId: Int,
  videoOwnerId: Int
) extends Event

final case class EventVideoCommentEdit(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  videoId: Int,
  videoOwnerId: Int
) extends Event

final case class EventVideoCommentRestore(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  video_id: Int,
  videoOwnerId: Int
) extends Event

final case class EventVideoCommentDelete(groupId: Int, ownerId: Int, id: Int, userId: Int, deleterId: Int, videoId: Int)
    extends Event

final case class EventWallPostNew(
  id: Int,
  ownerId: Int,
  fromId: Int,
  createdBy: Int,
  date: Long,
  text: String,
  replyOwnerId: Int,
  replyPostId: Int,
  friendsOnly: Option[Int] = Option.empty,
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
  groupId: Int,
  postponedId: Int
) extends Event

final case class EventWallRepost(
  id: Int,
  ownerId: Int,
  fromId: Int,
  createdBy: Int,
  date: Long,
  text: String,
  replyOwnerId: Int,
  replyPostId: Int,
  friendsOnly: Option[Int] = Option.empty,
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
  groupId: Int,
  postponedId: Int
) extends Event

final case class EventWallReplyNew(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  postId: Int,
  postOwnderId: Int
) extends Event

final case class EventWallReplyEdit(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  postId: Int,
  postOwnderId: Int
) extends Event

final case class EventWallReplyRestore(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  postId: Int,
  postOwnderId: Int
) extends Event

final case class EventWallReplyDelete(groupId: Int, ownerId: Int, id: Int, deleterId: Int, postId: Int) extends Event

final case class EventBoardPostNew(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  attachments: Vector[Attachment],
  groupId: Int,
  topicId: Int,
  topicOwnerId: Int
) extends Event

final case class EventBoardPostEdit(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  attachments: Vector[Attachment],
  groupId: Int,
  topicId: Int,
  topicOwnerId: Int
) extends Event

final case class EventBoardPostRestore(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  attachments: Vector[Attachment],
  groupId: Int,
  topicId: Int,
  topicOwnerId: Int
) extends Event

final case class EventBoardPostDelete(groupId: Int, topicId: Int, topicOwnerId: Int, id: Int) extends Event

final case class EventMarketCommentNew(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  marketOwnerId: Int,
  itemId: Int
) extends Event

final case class EventMarketCommentEdit(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  marketOwnerId: Int,
  itemId: Int
) extends Event

final case class EventMarketCommentRestore(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread,
  groupId: Int,
  marketOwnerId: Int,
  itemId: Int
) extends Event

final case class EventMarketCommentDelete(groupId: Int, ownerId: Int, id: Int, userId: Int, deleterId: Int, itemId: Int)
    extends Event

final case class EventGroupLeave(groupId: Int, userId: Int, self: Boolean) extends Event

final case class EventGroupJoin(groupId: Int, userId: Int) extends Event

final case class EventUserBlock(groupId: Int, adminId: Int, userId: Int, unblockDate: Long, comment: String)
    extends Event

final case class EventUserUnblock(groupId: Int, adminId: Int, userId: Int, byEndDate: Boolean) extends Event

final case class EventPollVoteNew(groupId: Int, ownerId: Int, pollId: Int, optionId: Int, userId: Int) extends Event

final case class EventGroupOfficersEdit(groupId: Int, adminId: Int, userId: Int) extends Event

final case class EventGroupChangeSettings(groupId: Int, userId: Int) extends Event

final case class EventGroupChangePhoto(groupId: Int, userId: Int, photo: Photo) extends Event

final case class EventVkPayTransaction(groupId: Int, fromId: Int, amount: Int, description: String, date: Long)
    extends Event
