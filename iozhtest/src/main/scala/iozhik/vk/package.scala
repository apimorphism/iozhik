package iozhik.vk

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import io.circe.HCursor

  implicit lazy val eventEncoder: Encoder[Event] = {

    case photo_comment_new: EventPhotoCommentNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("photoId",
              "photoOwnerId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      photo_comment_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("photo_comment_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case board_post_delete: EventBoardPostDelete => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("topicId", "topicOwnerId", "id")))
      board_post_delete.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("board_post_delete")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case video_comment_new: EventVideoCommentNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("videoId",
              "videoOwnerId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      video_comment_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("video_comment_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case market_comment_restore: EventMarketCommentRestore => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("marketOwnerId",
              "itemId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      market_comment_restore.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("market_comment_restore")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case video_comment_delete: EventVideoCommentDelete => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("ownerId", "id", "userId", "deleterId", "videoId")))
      video_comment_delete.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("video_comment_delete")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wall_repost: EventWallRepost => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List(
           "postponedId",
           "id",
           "ownerId",
           "fromId",
           "createdBy",
           "date",
           "text",
           "replyOwnerId",
           "replyPostId",
           "friendsOnly",
           "comments",
           "likes",
           "reposts",
           "views",
           "postType",
           "postSource",
           "attachments",
           "geo",
           "signerId",
           "copyHistory",
           "canPin",
           "canDelete",
           "canEdit",
           "isPinned",
           "markedAsAds",
           "isFavorite"
         )))
      wall_repost.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wall_repost")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wall_reply_new: EventWallReplyNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("postId",
              "postOwnderId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      wall_reply_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wall_reply_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case market_comment_edit: EventMarketCommentEdit => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("marketOwnerId",
              "itemId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      market_comment_edit.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("market_comment_edit")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case video_comment_edit: EventVideoCommentEdit => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("videoId",
              "videoOwnerId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      video_comment_edit.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("video_comment_edit")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case board_post_restore: EventBoardPostRestore => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("topicId", "topicOwnerId", "id", "fromId", "date", "text", "attachments")))
      board_post_restore.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("board_post_restore")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case poll_vote_new: EventPollVoteNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("ownerId", "pollId", "optionId", "userId")))
      poll_vote_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("poll_vote_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case photo_comment_restore: EventPhotoCommentRestore => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("photoId",
              "photoOwnerId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      photo_comment_restore.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("photo_comment_restore")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case video_new: EventVideoNew =>
      video_new.asJson.mapObject(_.add("type", Json.fromString("video_new")))

    case group_change_photo: EventGroupChangePhoto => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("userId", "photo")))
      group_change_photo.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("group_change_photo")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case message_allow: EventMessageAllow => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("userId", "key")))
      message_allow.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("message_allow")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wall_post_new: EventWallPostNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List(
           "postponedId",
           "id",
           "ownerId",
           "fromId",
           "createdBy",
           "date",
           "text",
           "replyOwnerId",
           "replyPostId",
           "friendsOnly",
           "comments",
           "likes",
           "reposts",
           "views",
           "postType",
           "postSource",
           "attachments",
           "geo",
           "signerId",
           "copyHistory",
           "canPin",
           "canDelete",
           "canEdit",
           "isPinned",
           "markedAsAds",
           "isFavorite"
         )))
      wall_post_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wall_post_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case vkpay_transaction: EventVkPayTransaction => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("fromId", "amount", "description", "date")))
      vkpay_transaction.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("vkpay_transaction")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wall_reply_edit: EventWallReplyEdit => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("postId",
              "postOwnderId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      wall_reply_edit.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wall_reply_edit")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case photo_comment_edit: EventPhotoCommentEdit => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("photoId",
              "photoOwnerId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      photo_comment_edit.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("photo_comment_edit")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case message_new: EventMessageNew =>
      message_new.asJson.mapObject(_.add("type", Json.fromString("message_new")))

    case group_leave: EventGroupLeave => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("userId", "self")))
      group_leave.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("group_leave")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wall_reply_delete: EventWallReplyDelete => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("ownerId", "id", "deleterId", "postId")))
      wall_reply_delete.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wall_reply_delete")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case group_change_settings: EventGroupChangeSettings => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("userId")))
      group_change_settings.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("group_change_settings")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case user_unblock: EventUserUnblock => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("adminId", "userId", "byEndDate")))
      user_unblock.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("user_unblock")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case group_officers_edit: EventGroupOfficersEdit => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("adminId", "userId")))
      group_officers_edit.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("group_officers_edit")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case user_block: EventUserBlock => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("adminId", "userId", "unblockDate", "comment")))
      user_block.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("user_block")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case market_comment_new: EventMarketCommentNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("marketOwnerId",
              "itemId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      market_comment_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("market_comment_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case audio_new: EventAudioNew =>
      audio_new.asJson.mapObject(_.add("type", Json.fromString("audio_new")))

    case photo_comment_delete: EventPhotoCommentDelete => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("ownerId", "id", "userId", "deleterId", "photoId")))
      photo_comment_delete.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("photo_comment_delete")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case board_post_edit: EventBoardPostEdit => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("topicId", "topicOwnerId", "id", "fromId", "date", "text", "attachments")))
      board_post_edit.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("board_post_edit")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case group_join: EventGroupJoin => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("userId")))
      group_join.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("group_join")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case message_deny: EventMessageDeny => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("groupId")
      val lvl1: List[(String, List[String])] = List(("object", List("userId")))
      message_deny.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("message_deny")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case message_edit: EventMessageEdit =>
      message_edit.asJson.mapObject(_.add("type", Json.fromString("message_edit")))

    case market_comment_delete: EventMarketCommentDelete => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("ownerId", "id", "userId", "deleterId", "itemId")))
      market_comment_delete.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("market_comment_delete")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case photo_new: EventPhotoNew =>
      photo_new.asJson.mapObject(_.add("type", Json.fromString("photo_new")))

    case video_comment_restore: EventVideoCommentRestore => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("video_id",
              "videoOwnerId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      video_comment_restore.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("video_comment_restore")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wall_reply_restore: EventWallReplyRestore => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object",
         List("postId",
              "postOwnderId",
              "id",
              "fromId",
              "date",
              "text",
              "replyToUser",
              "replyToComment",
              "attachments",
              "parentsStack",
              "thread")))
      wall_reply_restore.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wall_reply_restore")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case message_reply: EventMessageReply =>
      message_reply.asJson.mapObject(_.add("type", Json.fromString("message_reply")))

    case board_post_new: EventBoardPostNew => {
      import io.circe.JsonObject
      val lvl0: List[String] = List("groupId")
      val lvl1: List[(String, List[String])] = List(
        ("object", List("topicId", "topicOwnerId", "id", "fromId", "date", "text", "attachments")))
      board_post_new.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("board_post_new")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

  }
  implicit lazy val eventDecoder: Decoder[Event] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "photo_comment_new" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          photoId        <- Decoder[Int].prepare(_.downField("object").downField("photoId"))
          photoOwnerId   <- Decoder[Int].prepare(_.downField("object").downField("photoOwnerId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventPhotoCommentNew(
            groupId = groupId,
            photoId = photoId,
            photoOwnerId = photoOwnerId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "board_post_delete" =>
        for {
          groupId      <- Decoder[Int].prepare(_.downField("groupId"))
          topicId      <- Decoder[Int].prepare(_.downField("object").downField("topicId"))
          topicOwnerId <- Decoder[Int].prepare(_.downField("object").downField("topicOwnerId"))
          id           <- Decoder[Int].prepare(_.downField("object").downField("id"))
        } yield {
          EventBoardPostDelete(groupId = groupId,
                               topicId = topicId,
                               topicOwnerId = topicOwnerId,
                               id = id)
        }
      case "video_comment_new" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          videoId        <- Decoder[Int].prepare(_.downField("object").downField("videoId"))
          videoOwnerId   <- Decoder[Int].prepare(_.downField("object").downField("videoOwnerId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventVideoCommentNew(
            groupId = groupId,
            videoId = videoId,
            videoOwnerId = videoOwnerId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "market_comment_restore" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          marketOwnerId  <- Decoder[Int].prepare(_.downField("object").downField("marketOwnerId"))
          itemId         <- Decoder[Int].prepare(_.downField("object").downField("itemId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventMarketCommentRestore(
            groupId = groupId,
            marketOwnerId = marketOwnerId,
            itemId = itemId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "video_comment_delete" =>
        for {
          groupId   <- Decoder[Int].prepare(_.downField("groupId"))
          ownerId   <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          id        <- Decoder[Int].prepare(_.downField("object").downField("id"))
          userId    <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          deleterId <- Decoder[Int].prepare(_.downField("object").downField("deleterId"))
          videoId   <- Decoder[Int].prepare(_.downField("object").downField("videoId"))
        } yield {
          EventVideoCommentDelete(groupId = groupId,
                                  ownerId = ownerId,
                                  id = id,
                                  userId = userId,
                                  deleterId = deleterId,
                                  videoId = videoId)
        }
      case "wall_repost" =>
        for {
          groupId      <- Decoder[Int].prepare(_.downField("groupId"))
          postponedId  <- Decoder[Int].prepare(_.downField("object").downField("postponedId"))
          id           <- Decoder[Int].prepare(_.downField("object").downField("id"))
          ownerId      <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          fromId       <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          createdBy    <- Decoder[Int].prepare(_.downField("object").downField("createdBy"))
          date         <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text         <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyOwnerId <- Decoder[Int].prepare(_.downField("object").downField("replyOwnerId"))
          replyPostId  <- Decoder[Int].prepare(_.downField("object").downField("replyPostId"))
          friendsOnly <- Decoder[Option[Int]]
            .prepare(_.downField("object").downField("friendsOnly"))
          comments <- Decoder[WallPostCommentsAnon].prepare(
            _.downField("object").downField("comments"))
          likes <- Decoder[WallPostLikesAnon].prepare(_.downField("object").downField("likes"))
          reposts <- Decoder[WallPostReportsAnon].prepare(
            _.downField("object").downField("reposts"))
          views      <- Decoder[WallPostViewsAnon].prepare(_.downField("object").downField("views"))
          postType   <- Decoder[PostType].prepare(_.downField("object").downField("postType"))
          postSource <- Decoder[PostSource].prepare(_.downField("object").downField("postSource"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          geo      <- Decoder[Geo].prepare(_.downField("object").downField("geo"))
          signerId <- Decoder[Int].prepare(_.downField("object").downField("signerId"))
          copyHistory <- Decoder[Vector[WallPost]]
            .prepare(_.downField("object").downField("copyHistory"))
          canPin      <- Decoder[Boolean].prepare(_.downField("object").downField("canPin"))
          canDelete   <- Decoder[Boolean].prepare(_.downField("object").downField("canDelete"))
          canEdit     <- Decoder[Boolean].prepare(_.downField("object").downField("canEdit"))
          isPinned    <- Decoder[Boolean].prepare(_.downField("object").downField("isPinned"))
          markedAsAds <- Decoder[Boolean].prepare(_.downField("object").downField("markedAsAds"))
          isFavorite  <- Decoder[Boolean].prepare(_.downField("object").downField("isFavorite"))
        } yield {
          EventWallRepost(
            groupId = groupId,
            postponedId = postponedId,
            id = id,
            ownerId = ownerId,
            fromId = fromId,
            createdBy = createdBy,
            date = date,
            text = text,
            replyOwnerId = replyOwnerId,
            replyPostId = replyPostId,
            friendsOnly = friendsOnly,
            comments = comments,
            likes = likes,
            reposts = reposts,
            views = views,
            postType = postType,
            postSource = postSource,
            attachments = attachments,
            geo = geo,
            signerId = signerId,
            copyHistory = copyHistory,
            canPin = canPin,
            canDelete = canDelete,
            canEdit = canEdit,
            isPinned = isPinned,
            markedAsAds = markedAsAds,
            isFavorite = isFavorite
          )
        }
      case "wall_reply_new" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          postId         <- Decoder[Int].prepare(_.downField("object").downField("postId"))
          postOwnderId   <- Decoder[Int].prepare(_.downField("object").downField("postOwnderId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventWallReplyNew(
            groupId = groupId,
            postId = postId,
            postOwnderId = postOwnderId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "market_comment_edit" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          marketOwnerId  <- Decoder[Int].prepare(_.downField("object").downField("marketOwnerId"))
          itemId         <- Decoder[Int].prepare(_.downField("object").downField("itemId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventMarketCommentEdit(
            groupId = groupId,
            marketOwnerId = marketOwnerId,
            itemId = itemId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "video_comment_edit" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          videoId        <- Decoder[Int].prepare(_.downField("object").downField("videoId"))
          videoOwnerId   <- Decoder[Int].prepare(_.downField("object").downField("videoOwnerId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventVideoCommentEdit(
            groupId = groupId,
            videoId = videoId,
            videoOwnerId = videoOwnerId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "board_post_restore" =>
        for {
          groupId      <- Decoder[Int].prepare(_.downField("groupId"))
          topicId      <- Decoder[Int].prepare(_.downField("object").downField("topicId"))
          topicOwnerId <- Decoder[Int].prepare(_.downField("object").downField("topicOwnerId"))
          id           <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId       <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date         <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text         <- Decoder[String].prepare(_.downField("object").downField("text"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
        } yield {
          EventBoardPostRestore(groupId = groupId,
                                topicId = topicId,
                                topicOwnerId = topicOwnerId,
                                id = id,
                                fromId = fromId,
                                date = date,
                                text = text,
                                attachments = attachments)
        }
      case "poll_vote_new" =>
        for {
          groupId  <- Decoder[Int].prepare(_.downField("groupId"))
          ownerId  <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          pollId   <- Decoder[Int].prepare(_.downField("object").downField("pollId"))
          optionId <- Decoder[Int].prepare(_.downField("object").downField("optionId"))
          userId   <- Decoder[Int].prepare(_.downField("object").downField("userId"))
        } yield {
          EventPollVoteNew(groupId = groupId,
                           ownerId = ownerId,
                           pollId = pollId,
                           optionId = optionId,
                           userId = userId)
        }
      case "photo_comment_restore" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          photoId        <- Decoder[Int].prepare(_.downField("object").downField("photoId"))
          photoOwnerId   <- Decoder[Int].prepare(_.downField("object").downField("photoOwnerId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventPhotoCommentRestore(
            groupId = groupId,
            photoId = photoId,
            photoOwnerId = photoOwnerId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "video_new" => Decoder[EventVideoNew]
      case "group_change_photo" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          photo   <- Decoder[Photo].prepare(_.downField("object").downField("photo"))
        } yield {
          EventGroupChangePhoto(groupId = groupId, userId = userId, photo = photo)
        }
      case "message_allow" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          key     <- Decoder[String].prepare(_.downField("object").downField("key"))
        } yield {
          EventMessageAllow(groupId = groupId, userId = userId, key = key)
        }
      case "wall_post_new" =>
        for {
          groupId      <- Decoder[Int].prepare(_.downField("groupId"))
          postponedId  <- Decoder[Int].prepare(_.downField("object").downField("postponedId"))
          id           <- Decoder[Int].prepare(_.downField("object").downField("id"))
          ownerId      <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          fromId       <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          createdBy    <- Decoder[Int].prepare(_.downField("object").downField("createdBy"))
          date         <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text         <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyOwnerId <- Decoder[Int].prepare(_.downField("object").downField("replyOwnerId"))
          replyPostId  <- Decoder[Int].prepare(_.downField("object").downField("replyPostId"))
          friendsOnly <- Decoder[Option[Int]]
            .prepare(_.downField("object").downField("friendsOnly"))
          comments <- Decoder[WallPostCommentsAnon].prepare(
            _.downField("object").downField("comments"))
          likes <- Decoder[WallPostLikesAnon].prepare(_.downField("object").downField("likes"))
          reposts <- Decoder[WallPostReportsAnon].prepare(
            _.downField("object").downField("reposts"))
          views      <- Decoder[WallPostViewsAnon].prepare(_.downField("object").downField("views"))
          postType   <- Decoder[PostType].prepare(_.downField("object").downField("postType"))
          postSource <- Decoder[PostSource].prepare(_.downField("object").downField("postSource"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          geo      <- Decoder[Geo].prepare(_.downField("object").downField("geo"))
          signerId <- Decoder[Int].prepare(_.downField("object").downField("signerId"))
          copyHistory <- Decoder[Vector[WallPost]]
            .prepare(_.downField("object").downField("copyHistory"))
          canPin      <- Decoder[Boolean].prepare(_.downField("object").downField("canPin"))
          canDelete   <- Decoder[Boolean].prepare(_.downField("object").downField("canDelete"))
          canEdit     <- Decoder[Boolean].prepare(_.downField("object").downField("canEdit"))
          isPinned    <- Decoder[Boolean].prepare(_.downField("object").downField("isPinned"))
          markedAsAds <- Decoder[Boolean].prepare(_.downField("object").downField("markedAsAds"))
          isFavorite  <- Decoder[Boolean].prepare(_.downField("object").downField("isFavorite"))
        } yield {
          EventWallPostNew(
            groupId = groupId,
            postponedId = postponedId,
            id = id,
            ownerId = ownerId,
            fromId = fromId,
            createdBy = createdBy,
            date = date,
            text = text,
            replyOwnerId = replyOwnerId,
            replyPostId = replyPostId,
            friendsOnly = friendsOnly,
            comments = comments,
            likes = likes,
            reposts = reposts,
            views = views,
            postType = postType,
            postSource = postSource,
            attachments = attachments,
            geo = geo,
            signerId = signerId,
            copyHistory = copyHistory,
            canPin = canPin,
            canDelete = canDelete,
            canEdit = canEdit,
            isPinned = isPinned,
            markedAsAds = markedAsAds,
            isFavorite = isFavorite
          )
        }
      case "vkpay_transaction" =>
        for {
          groupId     <- Decoder[Int].prepare(_.downField("groupId"))
          fromId      <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          amount      <- Decoder[Int].prepare(_.downField("object").downField("amount"))
          description <- Decoder[String].prepare(_.downField("object").downField("description"))
          date        <- Decoder[Long].prepare(_.downField("object").downField("date"))
        } yield {
          EventVkPayTransaction(groupId = groupId,
                                fromId = fromId,
                                amount = amount,
                                description = description,
                                date = date)
        }
      case "wall_reply_edit" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          postId         <- Decoder[Int].prepare(_.downField("object").downField("postId"))
          postOwnderId   <- Decoder[Int].prepare(_.downField("object").downField("postOwnderId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventWallReplyEdit(
            groupId = groupId,
            postId = postId,
            postOwnderId = postOwnderId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "photo_comment_edit" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          photoId        <- Decoder[Int].prepare(_.downField("object").downField("photoId"))
          photoOwnerId   <- Decoder[Int].prepare(_.downField("object").downField("photoOwnerId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventPhotoCommentEdit(
            groupId = groupId,
            photoId = photoId,
            photoOwnerId = photoOwnerId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "message_new" => Decoder[EventMessageNew]
      case "group_leave" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          self    <- Decoder[Boolean].prepare(_.downField("object").downField("self"))
        } yield {
          EventGroupLeave(groupId = groupId, userId = userId, self = self)
        }
      case "wall_reply_delete" =>
        for {
          groupId   <- Decoder[Int].prepare(_.downField("groupId"))
          ownerId   <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          id        <- Decoder[Int].prepare(_.downField("object").downField("id"))
          deleterId <- Decoder[Int].prepare(_.downField("object").downField("deleterId"))
          postId    <- Decoder[Int].prepare(_.downField("object").downField("postId"))
        } yield {
          EventWallReplyDelete(groupId = groupId,
                               ownerId = ownerId,
                               id = id,
                               deleterId = deleterId,
                               postId = postId)
        }
      case "group_change_settings" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
        } yield {
          EventGroupChangeSettings(groupId = groupId, userId = userId)
        }
      case "user_unblock" =>
        for {
          groupId   <- Decoder[Int].prepare(_.downField("groupId"))
          adminId   <- Decoder[Int].prepare(_.downField("object").downField("adminId"))
          userId    <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          byEndDate <- Decoder[Boolean].prepare(_.downField("object").downField("byEndDate"))
        } yield {
          EventUserUnblock(groupId = groupId,
                           adminId = adminId,
                           userId = userId,
                           byEndDate = byEndDate)
        }
      case "group_officers_edit" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          adminId <- Decoder[Int].prepare(_.downField("object").downField("adminId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
        } yield {
          EventGroupOfficersEdit(groupId = groupId, adminId = adminId, userId = userId)
        }
      case "user_block" =>
        for {
          groupId     <- Decoder[Int].prepare(_.downField("groupId"))
          adminId     <- Decoder[Int].prepare(_.downField("object").downField("adminId"))
          userId      <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          unblockDate <- Decoder[Long].prepare(_.downField("object").downField("unblockDate"))
          comment     <- Decoder[String].prepare(_.downField("object").downField("comment"))
        } yield {
          EventUserBlock(groupId = groupId,
                         adminId = adminId,
                         userId = userId,
                         unblockDate = unblockDate,
                         comment = comment)
        }
      case "market_comment_new" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          marketOwnerId  <- Decoder[Int].prepare(_.downField("object").downField("marketOwnerId"))
          itemId         <- Decoder[Int].prepare(_.downField("object").downField("itemId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventMarketCommentNew(
            groupId = groupId,
            marketOwnerId = marketOwnerId,
            itemId = itemId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "audio_new" => Decoder[EventAudioNew]
      case "photo_comment_delete" =>
        for {
          groupId   <- Decoder[Int].prepare(_.downField("groupId"))
          ownerId   <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          id        <- Decoder[Int].prepare(_.downField("object").downField("id"))
          userId    <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          deleterId <- Decoder[Int].prepare(_.downField("object").downField("deleterId"))
          photoId   <- Decoder[Int].prepare(_.downField("object").downField("photoId"))
        } yield {
          EventPhotoCommentDelete(groupId = groupId,
                                  ownerId = ownerId,
                                  id = id,
                                  userId = userId,
                                  deleterId = deleterId,
                                  photoId = photoId)
        }
      case "board_post_edit" =>
        for {
          groupId      <- Decoder[Int].prepare(_.downField("groupId"))
          topicId      <- Decoder[Int].prepare(_.downField("object").downField("topicId"))
          topicOwnerId <- Decoder[Int].prepare(_.downField("object").downField("topicOwnerId"))
          id           <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId       <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date         <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text         <- Decoder[String].prepare(_.downField("object").downField("text"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
        } yield {
          EventBoardPostEdit(groupId = groupId,
                             topicId = topicId,
                             topicOwnerId = topicOwnerId,
                             id = id,
                             fromId = fromId,
                             date = date,
                             text = text,
                             attachments = attachments)
        }
      case "group_join" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
        } yield {
          EventGroupJoin(groupId = groupId, userId = userId)
        }
      case "message_deny" =>
        for {
          groupId <- Decoder[Int].prepare(_.downField("groupId"))
          userId  <- Decoder[Int].prepare(_.downField("object").downField("userId"))
        } yield {
          EventMessageDeny(groupId = groupId, userId = userId)
        }
      case "message_edit" => Decoder[EventMessageEdit]
      case "market_comment_delete" =>
        for {
          groupId   <- Decoder[Int].prepare(_.downField("groupId"))
          ownerId   <- Decoder[Int].prepare(_.downField("object").downField("ownerId"))
          id        <- Decoder[Int].prepare(_.downField("object").downField("id"))
          userId    <- Decoder[Int].prepare(_.downField("object").downField("userId"))
          deleterId <- Decoder[Int].prepare(_.downField("object").downField("deleterId"))
          itemId    <- Decoder[Int].prepare(_.downField("object").downField("itemId"))
        } yield {
          EventMarketCommentDelete(groupId = groupId,
                                   ownerId = ownerId,
                                   id = id,
                                   userId = userId,
                                   deleterId = deleterId,
                                   itemId = itemId)
        }
      case "photo_new" => Decoder[EventPhotoNew]
      case "video_comment_restore" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          video_id       <- Decoder[Int].prepare(_.downField("object").downField("video_id"))
          videoOwnerId   <- Decoder[Int].prepare(_.downField("object").downField("videoOwnerId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventVideoCommentRestore(
            groupId = groupId,
            video_id = video_id,
            videoOwnerId = videoOwnerId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "wall_reply_restore" =>
        for {
          groupId        <- Decoder[Int].prepare(_.downField("groupId"))
          postId         <- Decoder[Int].prepare(_.downField("object").downField("postId"))
          postOwnderId   <- Decoder[Int].prepare(_.downField("object").downField("postOwnderId"))
          id             <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId         <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date           <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text           <- Decoder[String].prepare(_.downField("object").downField("text"))
          replyToUser    <- Decoder[Int].prepare(_.downField("object").downField("replyToUser"))
          replyToComment <- Decoder[Int].prepare(_.downField("object").downField("replyToComment"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
          parentsStack <- Decoder[Vector[Int]]
            .prepare(_.downField("object").downField("parentsStack"))
          thread <- Decoder[CommentsThread].prepare(_.downField("object").downField("thread"))
        } yield {
          EventWallReplyRestore(
            groupId = groupId,
            postId = postId,
            postOwnderId = postOwnderId,
            id = id,
            fromId = fromId,
            date = date,
            text = text,
            replyToUser = replyToUser,
            replyToComment = replyToComment,
            attachments = attachments,
            parentsStack = parentsStack,
            thread = thread
          )
        }
      case "message_reply" => Decoder[EventMessageReply]
      case "board_post_new" =>
        for {
          groupId      <- Decoder[Int].prepare(_.downField("groupId"))
          topicId      <- Decoder[Int].prepare(_.downField("object").downField("topicId"))
          topicOwnerId <- Decoder[Int].prepare(_.downField("object").downField("topicOwnerId"))
          id           <- Decoder[Int].prepare(_.downField("object").downField("id"))
          fromId       <- Decoder[Int].prepare(_.downField("object").downField("fromId"))
          date         <- Decoder[Long].prepare(_.downField("object").downField("date"))
          text         <- Decoder[String].prepare(_.downField("object").downField("text"))
          attachments <- Decoder[Vector[Attachment]]
            .prepare(_.downField("object").downField("attachments"))
        } yield {
          EventBoardPostNew(groupId = groupId,
                            topicId = topicId,
                            topicOwnerId = topicOwnerId,
                            id = id,
                            fromId = fromId,
                            date = date,
                            text = text,
                            attachments = attachments)
        }
    }
  } yield value

  implicit lazy val eventmessagenewEncoder: Encoder[EventMessageNew] =
    (x: EventMessageNew) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "object"   -> x.`object`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmessagenewDecoder: Decoder[EventMessageNew] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _object  <- h.get[Message]("object")
      } yield {
        EventMessageNew(groupId = _groupId, `object` = _object)
      }
    }

  implicit lazy val eventmessagereplyEncoder: Encoder[EventMessageReply] =
    (x: EventMessageReply) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "object"   -> x.`object`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmessagereplyDecoder: Decoder[EventMessageReply] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _object  <- h.get[Message]("object")
      } yield {
        EventMessageReply(groupId = _groupId, `object` = _object)
      }
    }

  implicit lazy val eventmessageeditEncoder: Encoder[EventMessageEdit] =
    (x: EventMessageEdit) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "object"   -> x.`object`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmessageeditDecoder: Decoder[EventMessageEdit] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _object  <- h.get[Message]("object")
      } yield {
        EventMessageEdit(groupId = _groupId, `object` = _object)
      }
    }

  implicit lazy val eventmessageallowEncoder: Encoder[EventMessageAllow] =
    (x: EventMessageAllow) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "user_id"  -> x.userId.asJson,
          "key"      -> x.key.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmessageallowDecoder: Decoder[EventMessageAllow] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _userId  <- h.get[Int]("user_id")
        _key     <- h.get[String]("key")
      } yield {
        EventMessageAllow(groupId = _groupId, userId = _userId, key = _key)
      }
    }

  implicit lazy val eventmessagedenyEncoder: Encoder[EventMessageDeny] =
    (x: EventMessageDeny) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "user_id"  -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmessagedenyDecoder: Decoder[EventMessageDeny] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _userId  <- h.get[Int]("user_id")
      } yield {
        EventMessageDeny(groupId = _groupId, userId = _userId)
      }
    }

  implicit lazy val eventphotonewEncoder: Encoder[EventPhotoNew] =
    (x: EventPhotoNew) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "object"   -> x.`object`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventphotonewDecoder: Decoder[EventPhotoNew] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _object  <- h.get[Photo]("object")
      } yield {
        EventPhotoNew(groupId = _groupId, `object` = _object)
      }
    }

  implicit lazy val eventphotocommentnewEncoder: Encoder[EventPhotoCommentNew] =
    (x: EventPhotoCommentNew) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "photo_id"         -> x.photoId.asJson,
          "photo_owner_id"   -> x.photoOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventphotocommentnewDecoder: Decoder[EventPhotoCommentNew] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _photoId        <- h.get[Int]("photo_id")
        _photoOwnerId   <- h.get[Int]("photo_owner_id")
      } yield {
        EventPhotoCommentNew(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          photoId = _photoId,
          photoOwnerId = _photoOwnerId
        )
      }
    }

  implicit lazy val eventphotocommenteditEncoder: Encoder[EventPhotoCommentEdit] =
    (x: EventPhotoCommentEdit) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "photo_id"         -> x.photoId.asJson,
          "photo_owner_id"   -> x.photoOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventphotocommenteditDecoder: Decoder[EventPhotoCommentEdit] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _photoId        <- h.get[Int]("photo_id")
        _photoOwnerId   <- h.get[Int]("photo_owner_id")
      } yield {
        EventPhotoCommentEdit(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          photoId = _photoId,
          photoOwnerId = _photoOwnerId
        )
      }
    }

  implicit lazy val eventphotocommentrestoreEncoder: Encoder[EventPhotoCommentRestore] =
    (x: EventPhotoCommentRestore) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "photo_id"         -> x.photoId.asJson,
          "photo_owner_id"   -> x.photoOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventphotocommentrestoreDecoder: Decoder[EventPhotoCommentRestore] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _photoId        <- h.get[Int]("photo_id")
        _photoOwnerId   <- h.get[Int]("photo_owner_id")
      } yield {
        EventPhotoCommentRestore(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          photoId = _photoId,
          photoOwnerId = _photoOwnerId
        )
      }
    }

  implicit lazy val eventphotocommentdeleteEncoder: Encoder[EventPhotoCommentDelete] =
    (x: EventPhotoCommentDelete) => {
      Json.fromFields(
        List(
          "group_id"   -> x.groupId.asJson,
          "owner_id"   -> x.ownerId.asJson,
          "id"         -> x.id.asJson,
          "user_id"    -> x.userId.asJson,
          "deleter_id" -> x.deleterId.asJson,
          "photo_id"   -> x.photoId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventphotocommentdeleteDecoder: Decoder[EventPhotoCommentDelete] =
    Decoder.instance { h =>
      for {
        _groupId   <- h.get[Int]("group_id")
        _ownerId   <- h.get[Int]("owner_id")
        _id        <- h.get[Int]("id")
        _userId    <- h.get[Int]("user_id")
        _deleterId <- h.get[Int]("deleter_id")
        _photoId   <- h.get[Int]("photo_id")
      } yield {
        EventPhotoCommentDelete(groupId = _groupId,
                                ownerId = _ownerId,
                                id = _id,
                                userId = _userId,
                                deleterId = _deleterId,
                                photoId = _photoId)
      }
    }

  implicit lazy val eventaudionewEncoder: Encoder[EventAudioNew] =
    (x: EventAudioNew) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "object"   -> x.`object`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventaudionewDecoder: Decoder[EventAudioNew] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _object  <- h.get[Audio]("object")
      } yield {
        EventAudioNew(groupId = _groupId, `object` = _object)
      }
    }

  implicit lazy val eventvideonewEncoder: Encoder[EventVideoNew] =
    (x: EventVideoNew) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "object"   -> x.`object`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventvideonewDecoder: Decoder[EventVideoNew] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _object  <- h.get[Video]("object")
      } yield {
        EventVideoNew(groupId = _groupId, `object` = _object)
      }
    }

  implicit lazy val eventvideocommentnewEncoder: Encoder[EventVideoCommentNew] =
    (x: EventVideoCommentNew) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "video_id"         -> x.videoId.asJson,
          "video_owner_id"   -> x.videoOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventvideocommentnewDecoder: Decoder[EventVideoCommentNew] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _videoId        <- h.get[Int]("video_id")
        _videoOwnerId   <- h.get[Int]("video_owner_id")
      } yield {
        EventVideoCommentNew(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          videoId = _videoId,
          videoOwnerId = _videoOwnerId
        )
      }
    }

  implicit lazy val eventvideocommenteditEncoder: Encoder[EventVideoCommentEdit] =
    (x: EventVideoCommentEdit) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "video_id"         -> x.videoId.asJson,
          "video_owner_id"   -> x.videoOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventvideocommenteditDecoder: Decoder[EventVideoCommentEdit] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _videoId        <- h.get[Int]("video_id")
        _videoOwnerId   <- h.get[Int]("video_owner_id")
      } yield {
        EventVideoCommentEdit(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          videoId = _videoId,
          videoOwnerId = _videoOwnerId
        )
      }
    }

  implicit lazy val eventvideocommentrestoreEncoder: Encoder[EventVideoCommentRestore] =
    (x: EventVideoCommentRestore) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "video_id"         -> x.video_id.asJson,
          "video_owner_id"   -> x.videoOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventvideocommentrestoreDecoder: Decoder[EventVideoCommentRestore] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _video_id       <- h.get[Int]("video_id")
        _videoOwnerId   <- h.get[Int]("video_owner_id")
      } yield {
        EventVideoCommentRestore(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          video_id = _video_id,
          videoOwnerId = _videoOwnerId
        )
      }
    }

  implicit lazy val eventvideocommentdeleteEncoder: Encoder[EventVideoCommentDelete] =
    (x: EventVideoCommentDelete) => {
      Json.fromFields(
        List(
          "group_id"   -> x.groupId.asJson,
          "owner_id"   -> x.ownerId.asJson,
          "id"         -> x.id.asJson,
          "user_id"    -> x.userId.asJson,
          "deleter_id" -> x.deleterId.asJson,
          "video_id"   -> x.videoId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventvideocommentdeleteDecoder: Decoder[EventVideoCommentDelete] =
    Decoder.instance { h =>
      for {
        _groupId   <- h.get[Int]("group_id")
        _ownerId   <- h.get[Int]("owner_id")
        _id        <- h.get[Int]("id")
        _userId    <- h.get[Int]("user_id")
        _deleterId <- h.get[Int]("deleter_id")
        _videoId   <- h.get[Int]("video_id")
      } yield {
        EventVideoCommentDelete(groupId = _groupId,
                                ownerId = _ownerId,
                                id = _id,
                                userId = _userId,
                                deleterId = _deleterId,
                                videoId = _videoId)
      }
    }

  implicit lazy val eventwallpostnewEncoder: Encoder[EventWallPostNew] =
    (x: EventWallPostNew) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "owner_id"       -> x.ownerId.asJson,
          "from_id"        -> x.fromId.asJson,
          "created_by"     -> x.createdBy.asJson,
          "date"           -> x.date.asJson,
          "text"           -> x.text.asJson,
          "reply_owner_id" -> x.replyOwnerId.asJson,
          "reply_post_id"  -> x.replyPostId.asJson,
          "friends_only"   -> x.friendsOnly.asJson,
          "comments"       -> x.comments.asJson,
          "likes"          -> x.likes.asJson,
          "reposts"        -> x.reposts.asJson,
          "views"          -> x.views.asJson,
          "post_type"      -> x.postType.asJson,
          "post_source"    -> x.postSource.asJson,
          "attachments"    -> x.attachments.asJson,
          "geo"            -> x.geo.asJson,
          "signer_id"      -> x.signerId.asJson,
          "copy_history"   -> x.copyHistory.asJson,
          "can_pin"        -> x.canPin.asJson,
          "can_delete"     -> x.canDelete.asJson,
          "can_edit"       -> x.canEdit.asJson,
          "is_pinned"      -> x.isPinned.asJson,
          "marked_as_ads"  -> x.markedAsAds.asJson,
          "is_favorite"    -> x.isFavorite.asJson,
          "group_id"       -> x.groupId.asJson,
          "postponed_id"   -> x.postponedId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventwallpostnewDecoder: Decoder[EventWallPostNew] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _ownerId      <- h.get[Int]("owner_id")
        _fromId       <- h.get[Int]("from_id")
        _createdBy    <- h.get[Int]("created_by")
        _date         <- h.get[Long]("date")
        _text         <- h.get[String]("text")
        _replyOwnerId <- h.get[Int]("reply_owner_id")
        _replyPostId  <- h.get[Int]("reply_post_id")
        _friendsOnly  <- h.get[Option[Int]]("friends_only")
        _comments     <- h.get[WallPostCommentsAnon]("comments")
        _likes        <- h.get[WallPostLikesAnon]("likes")
        _reposts      <- h.get[WallPostReportsAnon]("reposts")
        _views        <- h.get[WallPostViewsAnon]("views")
        _postType     <- h.get[PostType]("post_type")
        _postSource   <- h.get[PostSource]("post_source")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _geo          <- h.get[Geo]("geo")
        _signerId     <- h.get[Int]("signer_id")
        _copyHistory  <- h.get[Vector[WallPost]]("copy_history")
        _canPin       <- h.get[Boolean]("can_pin")
        _canDelete    <- h.get[Boolean]("can_delete")
        _canEdit      <- h.get[Boolean]("can_edit")
        _isPinned     <- h.get[Boolean]("is_pinned")
        _markedAsAds  <- h.get[Boolean]("marked_as_ads")
        _isFavorite   <- h.get[Boolean]("is_favorite")
        _groupId      <- h.get[Int]("group_id")
        _postponedId  <- h.get[Int]("postponed_id")
      } yield {
        EventWallPostNew(
          id = _id,
          ownerId = _ownerId,
          fromId = _fromId,
          createdBy = _createdBy,
          date = _date,
          text = _text,
          replyOwnerId = _replyOwnerId,
          replyPostId = _replyPostId,
          friendsOnly = _friendsOnly,
          comments = _comments,
          likes = _likes,
          reposts = _reposts,
          views = _views,
          postType = _postType,
          postSource = _postSource,
          attachments = _attachments,
          geo = _geo,
          signerId = _signerId,
          copyHistory = _copyHistory,
          canPin = _canPin,
          canDelete = _canDelete,
          canEdit = _canEdit,
          isPinned = _isPinned,
          markedAsAds = _markedAsAds,
          isFavorite = _isFavorite,
          groupId = _groupId,
          postponedId = _postponedId
        )
      }
    }

  implicit lazy val eventwallrepostEncoder: Encoder[EventWallRepost] =
    (x: EventWallRepost) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "owner_id"       -> x.ownerId.asJson,
          "from_id"        -> x.fromId.asJson,
          "created_by"     -> x.createdBy.asJson,
          "date"           -> x.date.asJson,
          "text"           -> x.text.asJson,
          "reply_owner_id" -> x.replyOwnerId.asJson,
          "reply_post_id"  -> x.replyPostId.asJson,
          "friends_only"   -> x.friendsOnly.asJson,
          "comments"       -> x.comments.asJson,
          "likes"          -> x.likes.asJson,
          "reposts"        -> x.reposts.asJson,
          "views"          -> x.views.asJson,
          "post_type"      -> x.postType.asJson,
          "post_source"    -> x.postSource.asJson,
          "attachments"    -> x.attachments.asJson,
          "geo"            -> x.geo.asJson,
          "signer_id"      -> x.signerId.asJson,
          "copy_history"   -> x.copyHistory.asJson,
          "can_pin"        -> x.canPin.asJson,
          "can_delete"     -> x.canDelete.asJson,
          "can_edit"       -> x.canEdit.asJson,
          "is_pinned"      -> x.isPinned.asJson,
          "marked_as_ads"  -> x.markedAsAds.asJson,
          "is_favorite"    -> x.isFavorite.asJson,
          "group_id"       -> x.groupId.asJson,
          "postponed_id"   -> x.postponedId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventwallrepostDecoder: Decoder[EventWallRepost] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _ownerId      <- h.get[Int]("owner_id")
        _fromId       <- h.get[Int]("from_id")
        _createdBy    <- h.get[Int]("created_by")
        _date         <- h.get[Long]("date")
        _text         <- h.get[String]("text")
        _replyOwnerId <- h.get[Int]("reply_owner_id")
        _replyPostId  <- h.get[Int]("reply_post_id")
        _friendsOnly  <- h.get[Option[Int]]("friends_only")
        _comments     <- h.get[WallPostCommentsAnon]("comments")
        _likes        <- h.get[WallPostLikesAnon]("likes")
        _reposts      <- h.get[WallPostReportsAnon]("reposts")
        _views        <- h.get[WallPostViewsAnon]("views")
        _postType     <- h.get[PostType]("post_type")
        _postSource   <- h.get[PostSource]("post_source")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _geo          <- h.get[Geo]("geo")
        _signerId     <- h.get[Int]("signer_id")
        _copyHistory  <- h.get[Vector[WallPost]]("copy_history")
        _canPin       <- h.get[Boolean]("can_pin")
        _canDelete    <- h.get[Boolean]("can_delete")
        _canEdit      <- h.get[Boolean]("can_edit")
        _isPinned     <- h.get[Boolean]("is_pinned")
        _markedAsAds  <- h.get[Boolean]("marked_as_ads")
        _isFavorite   <- h.get[Boolean]("is_favorite")
        _groupId      <- h.get[Int]("group_id")
        _postponedId  <- h.get[Int]("postponed_id")
      } yield {
        EventWallRepost(
          id = _id,
          ownerId = _ownerId,
          fromId = _fromId,
          createdBy = _createdBy,
          date = _date,
          text = _text,
          replyOwnerId = _replyOwnerId,
          replyPostId = _replyPostId,
          friendsOnly = _friendsOnly,
          comments = _comments,
          likes = _likes,
          reposts = _reposts,
          views = _views,
          postType = _postType,
          postSource = _postSource,
          attachments = _attachments,
          geo = _geo,
          signerId = _signerId,
          copyHistory = _copyHistory,
          canPin = _canPin,
          canDelete = _canDelete,
          canEdit = _canEdit,
          isPinned = _isPinned,
          markedAsAds = _markedAsAds,
          isFavorite = _isFavorite,
          groupId = _groupId,
          postponedId = _postponedId
        )
      }
    }

  implicit lazy val eventwallreplynewEncoder: Encoder[EventWallReplyNew] =
    (x: EventWallReplyNew) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "post_id"          -> x.postId.asJson,
          "post_ownder_id"   -> x.postOwnderId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventwallreplynewDecoder: Decoder[EventWallReplyNew] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _postId         <- h.get[Int]("post_id")
        _postOwnderId   <- h.get[Int]("post_ownder_id")
      } yield {
        EventWallReplyNew(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          postId = _postId,
          postOwnderId = _postOwnderId
        )
      }
    }

  implicit lazy val eventwallreplyeditEncoder: Encoder[EventWallReplyEdit] =
    (x: EventWallReplyEdit) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "post_id"          -> x.postId.asJson,
          "post_ownder_id"   -> x.postOwnderId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventwallreplyeditDecoder: Decoder[EventWallReplyEdit] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _postId         <- h.get[Int]("post_id")
        _postOwnderId   <- h.get[Int]("post_ownder_id")
      } yield {
        EventWallReplyEdit(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          postId = _postId,
          postOwnderId = _postOwnderId
        )
      }
    }

  implicit lazy val eventwallreplyrestoreEncoder: Encoder[EventWallReplyRestore] =
    (x: EventWallReplyRestore) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "post_id"          -> x.postId.asJson,
          "post_ownder_id"   -> x.postOwnderId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventwallreplyrestoreDecoder: Decoder[EventWallReplyRestore] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _postId         <- h.get[Int]("post_id")
        _postOwnderId   <- h.get[Int]("post_ownder_id")
      } yield {
        EventWallReplyRestore(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          postId = _postId,
          postOwnderId = _postOwnderId
        )
      }
    }

  implicit lazy val eventwallreplydeleteEncoder: Encoder[EventWallReplyDelete] =
    (x: EventWallReplyDelete) => {
      Json.fromFields(
        List(
          "group_id"   -> x.groupId.asJson,
          "owner_id"   -> x.ownerId.asJson,
          "id"         -> x.id.asJson,
          "deleter_id" -> x.deleterId.asJson,
          "post_id"    -> x.postId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventwallreplydeleteDecoder: Decoder[EventWallReplyDelete] =
    Decoder.instance { h =>
      for {
        _groupId   <- h.get[Int]("group_id")
        _ownerId   <- h.get[Int]("owner_id")
        _id        <- h.get[Int]("id")
        _deleterId <- h.get[Int]("deleter_id")
        _postId    <- h.get[Int]("post_id")
      } yield {
        EventWallReplyDelete(groupId = _groupId,
                             ownerId = _ownerId,
                             id = _id,
                             deleterId = _deleterId,
                             postId = _postId)
      }
    }

  implicit lazy val eventboardpostnewEncoder: Encoder[EventBoardPostNew] =
    (x: EventBoardPostNew) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "from_id"        -> x.fromId.asJson,
          "date"           -> x.date.asJson,
          "text"           -> x.text.asJson,
          "attachments"    -> x.attachments.asJson,
          "group_id"       -> x.groupId.asJson,
          "topic_id"       -> x.topicId.asJson,
          "topic_owner_id" -> x.topicOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventboardpostnewDecoder: Decoder[EventBoardPostNew] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _fromId       <- h.get[Int]("from_id")
        _date         <- h.get[Long]("date")
        _text         <- h.get[String]("text")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _groupId      <- h.get[Int]("group_id")
        _topicId      <- h.get[Int]("topic_id")
        _topicOwnerId <- h.get[Int]("topic_owner_id")
      } yield {
        EventBoardPostNew(id = _id,
                          fromId = _fromId,
                          date = _date,
                          text = _text,
                          attachments = _attachments,
                          groupId = _groupId,
                          topicId = _topicId,
                          topicOwnerId = _topicOwnerId)
      }
    }

  implicit lazy val eventboardposteditEncoder: Encoder[EventBoardPostEdit] =
    (x: EventBoardPostEdit) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "from_id"        -> x.fromId.asJson,
          "date"           -> x.date.asJson,
          "text"           -> x.text.asJson,
          "attachments"    -> x.attachments.asJson,
          "group_id"       -> x.groupId.asJson,
          "topic_id"       -> x.topicId.asJson,
          "topic_owner_id" -> x.topicOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventboardposteditDecoder: Decoder[EventBoardPostEdit] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _fromId       <- h.get[Int]("from_id")
        _date         <- h.get[Long]("date")
        _text         <- h.get[String]("text")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _groupId      <- h.get[Int]("group_id")
        _topicId      <- h.get[Int]("topic_id")
        _topicOwnerId <- h.get[Int]("topic_owner_id")
      } yield {
        EventBoardPostEdit(id = _id,
                           fromId = _fromId,
                           date = _date,
                           text = _text,
                           attachments = _attachments,
                           groupId = _groupId,
                           topicId = _topicId,
                           topicOwnerId = _topicOwnerId)
      }
    }

  implicit lazy val eventboardpostrestoreEncoder: Encoder[EventBoardPostRestore] =
    (x: EventBoardPostRestore) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "from_id"        -> x.fromId.asJson,
          "date"           -> x.date.asJson,
          "text"           -> x.text.asJson,
          "attachments"    -> x.attachments.asJson,
          "group_id"       -> x.groupId.asJson,
          "topic_id"       -> x.topicId.asJson,
          "topic_owner_id" -> x.topicOwnerId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventboardpostrestoreDecoder: Decoder[EventBoardPostRestore] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _fromId       <- h.get[Int]("from_id")
        _date         <- h.get[Long]("date")
        _text         <- h.get[String]("text")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _groupId      <- h.get[Int]("group_id")
        _topicId      <- h.get[Int]("topic_id")
        _topicOwnerId <- h.get[Int]("topic_owner_id")
      } yield {
        EventBoardPostRestore(id = _id,
                              fromId = _fromId,
                              date = _date,
                              text = _text,
                              attachments = _attachments,
                              groupId = _groupId,
                              topicId = _topicId,
                              topicOwnerId = _topicOwnerId)
      }
    }

  implicit lazy val eventboardpostdeleteEncoder: Encoder[EventBoardPostDelete] =
    (x: EventBoardPostDelete) => {
      Json.fromFields(
        List(
          "group_id"       -> x.groupId.asJson,
          "topic_id"       -> x.topicId.asJson,
          "topic_owner_id" -> x.topicOwnerId.asJson,
          "id"             -> x.id.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventboardpostdeleteDecoder: Decoder[EventBoardPostDelete] =
    Decoder.instance { h =>
      for {
        _groupId      <- h.get[Int]("group_id")
        _topicId      <- h.get[Int]("topic_id")
        _topicOwnerId <- h.get[Int]("topic_owner_id")
        _id           <- h.get[Int]("id")
      } yield {
        EventBoardPostDelete(groupId = _groupId,
                             topicId = _topicId,
                             topicOwnerId = _topicOwnerId,
                             id = _id)
      }
    }

  implicit lazy val eventmarketcommentnewEncoder: Encoder[EventMarketCommentNew] =
    (x: EventMarketCommentNew) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "market_owner_id"  -> x.marketOwnerId.asJson,
          "item_id"          -> x.itemId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmarketcommentnewDecoder: Decoder[EventMarketCommentNew] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _marketOwnerId  <- h.get[Int]("market_owner_id")
        _itemId         <- h.get[Int]("item_id")
      } yield {
        EventMarketCommentNew(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          marketOwnerId = _marketOwnerId,
          itemId = _itemId
        )
      }
    }

  implicit lazy val eventmarketcommenteditEncoder: Encoder[EventMarketCommentEdit] =
    (x: EventMarketCommentEdit) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "market_owner_id"  -> x.marketOwnerId.asJson,
          "item_id"          -> x.itemId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmarketcommenteditDecoder: Decoder[EventMarketCommentEdit] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _marketOwnerId  <- h.get[Int]("market_owner_id")
        _itemId         <- h.get[Int]("item_id")
      } yield {
        EventMarketCommentEdit(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          marketOwnerId = _marketOwnerId,
          itemId = _itemId
        )
      }
    }

  implicit lazy val eventmarketcommentrestoreEncoder: Encoder[EventMarketCommentRestore] =
    (x: EventMarketCommentRestore) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson,
          "group_id"         -> x.groupId.asJson,
          "market_owner_id"  -> x.marketOwnerId.asJson,
          "item_id"          -> x.itemId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmarketcommentrestoreDecoder: Decoder[EventMarketCommentRestore] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
        _groupId        <- h.get[Int]("group_id")
        _marketOwnerId  <- h.get[Int]("market_owner_id")
        _itemId         <- h.get[Int]("item_id")
      } yield {
        EventMarketCommentRestore(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread,
          groupId = _groupId,
          marketOwnerId = _marketOwnerId,
          itemId = _itemId
        )
      }
    }

  implicit lazy val eventmarketcommentdeleteEncoder: Encoder[EventMarketCommentDelete] =
    (x: EventMarketCommentDelete) => {
      Json.fromFields(
        List(
          "group_id"   -> x.groupId.asJson,
          "owner_id"   -> x.ownerId.asJson,
          "id"         -> x.id.asJson,
          "user_id"    -> x.userId.asJson,
          "deleter_id" -> x.deleterId.asJson,
          "item_id"    -> x.itemId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventmarketcommentdeleteDecoder: Decoder[EventMarketCommentDelete] =
    Decoder.instance { h =>
      for {
        _groupId   <- h.get[Int]("group_id")
        _ownerId   <- h.get[Int]("owner_id")
        _id        <- h.get[Int]("id")
        _userId    <- h.get[Int]("user_id")
        _deleterId <- h.get[Int]("deleter_id")
        _itemId    <- h.get[Int]("item_id")
      } yield {
        EventMarketCommentDelete(groupId = _groupId,
                                 ownerId = _ownerId,
                                 id = _id,
                                 userId = _userId,
                                 deleterId = _deleterId,
                                 itemId = _itemId)
      }
    }

  implicit lazy val eventgroupleaveEncoder: Encoder[EventGroupLeave] =
    (x: EventGroupLeave) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "user_id"  -> x.userId.asJson,
          "self"     -> x.self.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventgroupleaveDecoder: Decoder[EventGroupLeave] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _userId  <- h.get[Int]("user_id")
        _self    <- h.get[Boolean]("self")
      } yield {
        EventGroupLeave(groupId = _groupId, userId = _userId, self = _self)
      }
    }

  implicit lazy val eventgroupjoinEncoder: Encoder[EventGroupJoin] =
    (x: EventGroupJoin) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "user_id"  -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventgroupjoinDecoder: Decoder[EventGroupJoin] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _userId  <- h.get[Int]("user_id")
      } yield {
        EventGroupJoin(groupId = _groupId, userId = _userId)
      }
    }

  implicit lazy val eventuserblockEncoder: Encoder[EventUserBlock] =
    (x: EventUserBlock) => {
      Json.fromFields(
        List(
          "group_id"     -> x.groupId.asJson,
          "admin_id"     -> x.adminId.asJson,
          "user_id"      -> x.userId.asJson,
          "unblock_date" -> x.unblockDate.asJson,
          "comment"      -> x.comment.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventuserblockDecoder: Decoder[EventUserBlock] =
    Decoder.instance { h =>
      for {
        _groupId     <- h.get[Int]("group_id")
        _adminId     <- h.get[Int]("admin_id")
        _userId      <- h.get[Int]("user_id")
        _unblockDate <- h.get[Long]("unblock_date")
        _comment     <- h.get[String]("comment")
      } yield {
        EventUserBlock(groupId = _groupId,
                       adminId = _adminId,
                       userId = _userId,
                       unblockDate = _unblockDate,
                       comment = _comment)
      }
    }

  implicit lazy val eventuserunblockEncoder: Encoder[EventUserUnblock] =
    (x: EventUserUnblock) => {
      Json.fromFields(
        List(
          "group_id"    -> x.groupId.asJson,
          "admin_id"    -> x.adminId.asJson,
          "user_id"     -> x.userId.asJson,
          "by_end_date" -> x.byEndDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventuserunblockDecoder: Decoder[EventUserUnblock] =
    Decoder.instance { h =>
      for {
        _groupId   <- h.get[Int]("group_id")
        _adminId   <- h.get[Int]("admin_id")
        _userId    <- h.get[Int]("user_id")
        _byEndDate <- h.get[Boolean]("by_end_date")
      } yield {
        EventUserUnblock(groupId = _groupId,
                         adminId = _adminId,
                         userId = _userId,
                         byEndDate = _byEndDate)
      }
    }

  implicit lazy val eventpollvotenewEncoder: Encoder[EventPollVoteNew] =
    (x: EventPollVoteNew) => {
      Json.fromFields(
        List(
          "group_id"  -> x.groupId.asJson,
          "owner_id"  -> x.ownerId.asJson,
          "poll_id"   -> x.pollId.asJson,
          "option_id" -> x.optionId.asJson,
          "user_id"   -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventpollvotenewDecoder: Decoder[EventPollVoteNew] =
    Decoder.instance { h =>
      for {
        _groupId  <- h.get[Int]("group_id")
        _ownerId  <- h.get[Int]("owner_id")
        _pollId   <- h.get[Int]("poll_id")
        _optionId <- h.get[Int]("option_id")
        _userId   <- h.get[Int]("user_id")
      } yield {
        EventPollVoteNew(groupId = _groupId,
                         ownerId = _ownerId,
                         pollId = _pollId,
                         optionId = _optionId,
                         userId = _userId)
      }
    }

  implicit lazy val eventgroupofficerseditEncoder: Encoder[EventGroupOfficersEdit] =
    (x: EventGroupOfficersEdit) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "admin_id" -> x.adminId.asJson,
          "user_id"  -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventgroupofficerseditDecoder: Decoder[EventGroupOfficersEdit] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _adminId <- h.get[Int]("admin_id")
        _userId  <- h.get[Int]("user_id")
      } yield {
        EventGroupOfficersEdit(groupId = _groupId, adminId = _adminId, userId = _userId)
      }
    }

  implicit lazy val eventgroupchangesettingsEncoder: Encoder[EventGroupChangeSettings] =
    (x: EventGroupChangeSettings) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "user_id"  -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventgroupchangesettingsDecoder: Decoder[EventGroupChangeSettings] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _userId  <- h.get[Int]("user_id")
      } yield {
        EventGroupChangeSettings(groupId = _groupId, userId = _userId)
      }
    }

  implicit lazy val eventgroupchangephotoEncoder: Encoder[EventGroupChangePhoto] =
    (x: EventGroupChangePhoto) => {
      Json.fromFields(
        List(
          "group_id" -> x.groupId.asJson,
          "user_id"  -> x.userId.asJson,
          "photo"    -> x.photo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventgroupchangephotoDecoder: Decoder[EventGroupChangePhoto] =
    Decoder.instance { h =>
      for {
        _groupId <- h.get[Int]("group_id")
        _userId  <- h.get[Int]("user_id")
        _photo   <- h.get[Photo]("photo")
      } yield {
        EventGroupChangePhoto(groupId = _groupId, userId = _userId, photo = _photo)
      }
    }

  implicit lazy val eventvkpaytransactionEncoder: Encoder[EventVkPayTransaction] =
    (x: EventVkPayTransaction) => {
      Json.fromFields(
        List(
          "group_id"    -> x.groupId.asJson,
          "from_id"     -> x.fromId.asJson,
          "amount"      -> x.amount.asJson,
          "description" -> x.description.asJson,
          "date"        -> x.date.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val eventvkpaytransactionDecoder: Decoder[EventVkPayTransaction] =
    Decoder.instance { h =>
      for {
        _groupId     <- h.get[Int]("group_id")
        _fromId      <- h.get[Int]("from_id")
        _amount      <- h.get[Int]("amount")
        _description <- h.get[String]("description")
        _date        <- h.get[Long]("date")
      } yield {
        EventVkPayTransaction(groupId = _groupId,
                              fromId = _fromId,
                              amount = _amount,
                              description = _description,
                              date = _date)
      }
    }

  implicit lazy val audioEncoder: Encoder[Audio] =
    (x: Audio) => {
      Json.fromFields(
        List(
          "id"        -> x.id.asJson,
          "owner_id"  -> x.ownerId.asJson,
          "artist"    -> x.artist.asJson,
          "title"     -> x.title.asJson,
          "duration"  -> x.duration.asJson,
          "url"       -> x.url.asJson,
          "lyrics_id" -> x.lyricsId.asJson,
          "album_id"  -> x.albumId.asJson,
          "genre_id"  -> x.genreId.asJson,
          "date"      -> x.date.asJson,
          "no_search" -> x.noSearch.asJson,
          "is_hq"     -> x.isHq.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val audioDecoder: Decoder[Audio] =
    Decoder.instance { h =>
      for {
        _id       <- h.get[Int]("id")
        _ownerId  <- h.get[Int]("owner_id")
        _artist   <- h.get[String]("artist")
        _title    <- h.get[String]("title")
        _duration <- h.get[Int]("duration")
        _url      <- h.get[String]("url")
        _lyricsId <- h.get[Int]("lyrics_id")
        _albumId  <- h.get[Int]("album_id")
        _genreId  <- h.get[Int]("genre_id")
        _date     <- h.get[Int]("date")
        _noSearch <- h.get[Option[Int]]("no_search")
        _isHq     <- h.get[Option[Int]]("is_hq")
      } yield {
        Audio(
          id = _id,
          ownerId = _ownerId,
          artist = _artist,
          title = _title,
          duration = _duration,
          url = _url,
          lyricsId = _lyricsId,
          albumId = _albumId,
          genreId = _genreId,
          date = _date,
          noSearch = _noSearch,
          isHq = _isHq
        )
      }
    }

  implicit lazy val sizedphotoEncoder: Encoder[SizedPhoto] =
    (x: SizedPhoto) => {
      Json.fromFields(
        List(
          "src"    -> x.src.asJson,
          "width"  -> x.width.asJson,
          "height" -> x.height.asJson,
          "type"   -> x.`type`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sizedphotoDecoder: Decoder[SizedPhoto] =
    Decoder.instance { h =>
      for {
        _src    <- h.get[String]("src")
        _width  <- h.get[Int]("width")
        _height <- h.get[Int]("height")
        _type   <- h.get[String]("type")
      } yield {
        SizedPhoto(src = _src, width = _width, height = _height, `type` = _type)
      }
    }

  implicit lazy val photoEncoder: Encoder[Photo] =
    (x: Photo) => {
      Json.fromFields(
        List(
          "id"       -> x.id.asJson,
          "album_id" -> x.albumId.asJson,
          "owner_id" -> x.ownerId.asJson,
          "user_id"  -> x.userId.asJson,
          "text"     -> x.text.asJson,
          "date"     -> x.date.asJson,
          "sizes"    -> x.sizes.asJson,
          "width"    -> x.width.asJson,
          "height"   -> x.height.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val photoDecoder: Decoder[Photo] =
    Decoder.instance { h =>
      for {
        _id      <- h.get[Int]("id")
        _albumId <- h.get[Int]("album_id")
        _ownerId <- h.get[Int]("owner_id")
        _userId  <- h.get[Int]("user_id")
        _text    <- h.get[String]("text")
        _date    <- h.get[Long]("date")
        _sizes   <- h.get[Vector[SizedPhoto]]("sizes")
        _width   <- h.get[Option[Int]]("width")
        _height  <- h.get[Option[Int]]("height")
      } yield {
        Photo(id = _id,
              albumId = _albumId,
              ownerId = _ownerId,
              userId = _userId,
              text = _text,
              date = _date,
              sizes = _sizes,
              width = _width,
              height = _height)
      }
    }

  implicit lazy val videoEncoder: Encoder[Video] =
    (x: Video) => {
      Json.fromFields(
        List(
          "id"              -> x.id.asJson,
          "owner_id"        -> x.ownerId.asJson,
          "title"           -> x.title.asJson,
          "description"     -> x.description.asJson,
          "duration"        -> x.duration.asJson,
          "photo130"        -> x.photo130.asJson,
          "photo320"        -> x.photo320.asJson,
          "photo640"        -> x.photo640.asJson,
          "photo800"        -> x.photo800.asJson,
          "photo1280"       -> x.photo1280.asJson,
          "first_frame130"  -> x.firstFrame130.asJson,
          "first_frame320"  -> x.firstFrame320.asJson,
          "first_frame640"  -> x.firstFrame640.asJson,
          "first_frame800"  -> x.firstFrame800.asJson,
          "first_frame1280" -> x.firstFrame1280.asJson,
          "date"            -> x.date.asJson,
          "adding_date"     -> x.addingDate.asJson,
          "views"           -> x.views.asJson,
          "comments"        -> x.comments.asJson,
          "player"          -> x.player.asJson,
          "platform"        -> x.platform.asJson,
          "can_edit"        -> x.canEdit.asJson,
          "can_add"         -> x.canAdd.asJson,
          "is_private"      -> x.isPrivate.asJson,
          "access_key"      -> x.accessKey.asJson,
          "processing"      -> x.processing.asJson,
          "live"            -> x.live.asJson,
          "upcoming"        -> x.upcoming.asJson,
          "is_favorite"     -> x.isFavorite.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videoDecoder: Decoder[Video] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _ownerId        <- h.get[Int]("owner_id")
        _title          <- h.get[String]("title")
        _description    <- h.get[String]("description")
        _duration       <- h.get[Int]("duration")
        _photo130       <- h.get[String]("photo130")
        _photo320       <- h.get[String]("photo320")
        _photo640       <- h.get[String]("photo640")
        _photo800       <- h.get[String]("photo800")
        _photo1280      <- h.get[String]("photo1280")
        _firstFrame130  <- h.get[String]("first_frame130")
        _firstFrame320  <- h.get[String]("first_frame320")
        _firstFrame640  <- h.get[String]("first_frame640")
        _firstFrame800  <- h.get[String]("first_frame800")
        _firstFrame1280 <- h.get[String]("first_frame1280")
        _date           <- h.get[Long]("date")
        _addingDate     <- h.get[Long]("adding_date")
        _views          <- h.get[Int]("views")
        _comments       <- h.get[Int]("comments")
        _player         <- h.get[String]("player")
        _platform       <- h.get[String]("platform")
        _canEdit        <- h.get[Option[Int]]("can_edit")
        _canAdd         <- h.get[Int]("can_add")
        _isPrivate      <- h.get[Option[Int]]("is_private")
        _accessKey      <- h.get[Int]("access_key")
        _processing     <- h.get[Option[Int]]("processing")
        _live           <- h.get[Option[Int]]("live")
        _upcoming       <- h.get[Option[Int]]("upcoming")
        _isFavorite     <- h.get[Boolean]("is_favorite")
      } yield {
        Video(
          id = _id,
          ownerId = _ownerId,
          title = _title,
          description = _description,
          duration = _duration,
          photo130 = _photo130,
          photo320 = _photo320,
          photo640 = _photo640,
          photo800 = _photo800,
          photo1280 = _photo1280,
          firstFrame130 = _firstFrame130,
          firstFrame320 = _firstFrame320,
          firstFrame640 = _firstFrame640,
          firstFrame800 = _firstFrame800,
          firstFrame1280 = _firstFrame1280,
          date = _date,
          addingDate = _addingDate,
          views = _views,
          comments = _comments,
          player = _player,
          platform = _platform,
          canEdit = _canEdit,
          canAdd = _canAdd,
          isPrivate = _isPrivate,
          accessKey = _accessKey,
          processing = _processing,
          live = _live,
          upcoming = _upcoming,
          isFavorite = _isFavorite
        )
      }
    }

  implicit lazy val attachmentEncoder: Encoder[Attachment] = {
    case photo: AttachmentPhoto => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case video: AttachmentVideo => video.asJson.mapObject(_.add("type", Json.fromString("video")))
  }
  implicit lazy val attachmentDecoder: Decoder[Attachment] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "photo" => Decoder[AttachmentPhoto]
      case "video" => Decoder[AttachmentVideo]
    }
  } yield value

  implicit lazy val attachmentphotoEncoder: Encoder[AttachmentPhoto] =
    (x: AttachmentPhoto) => {
      Json.fromFields(
        List(
          "photo" -> x.photo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val attachmentphotoDecoder: Decoder[AttachmentPhoto] =
    Decoder.instance { h =>
      for {
        _photo <- h.get[Photo]("photo")
      } yield {
        AttachmentPhoto(photo = _photo)
      }
    }

  implicit lazy val attachmentvideoEncoder: Encoder[AttachmentVideo] =
    (x: AttachmentVideo) => {
      Json.fromFields(
        List(
          "video" -> x.video.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val attachmentvideoDecoder: Decoder[AttachmentVideo] =
    Decoder.instance { h =>
      for {
        _video <- h.get[Video]("video")
      } yield {
        AttachmentVideo(video = _video)
      }
    }

  implicit lazy val placeEncoder: Encoder[Place] =
    (x: Place) => {
      Json.fromFields(
        List(
          "id"          -> x.id.asJson,
          "title"       -> x.title.asJson,
          "latitude"    -> x.latitude.asJson,
          "longitude"   -> x.longitude.asJson,
          "created"     -> x.created.asJson,
          "icon"        -> x.icon.asJson,
          "country"     -> x.country.asJson,
          "city"        -> x.city.asJson,
          "type"        -> x.`type`.asJson,
          "group_id"    -> x.groupId.asJson,
          "group_photo" -> x.groupPhoto.asJson,
          "checkins"    -> x.checkins.asJson,
          "updated"     -> x.updated.asJson,
          "address"     -> x.address.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val placeDecoder: Decoder[Place] =
    Decoder.instance { h =>
      for {
        _id         <- h.get[Int]("id")
        _title      <- h.get[String]("title")
        _latitude   <- h.get[Double]("latitude")
        _longitude  <- h.get[Double]("longitude")
        _created    <- h.get[Int]("created")
        _icon       <- h.get[String]("icon")
        _country    <- h.get[String]("country")
        _city       <- h.get[String]("city")
        _type       <- h.get[Option[Int]]("type")
        _groupId    <- h.get[Option[Int]]("group_id")
        _groupPhoto <- h.get[Option[String]]("group_photo")
        _checkins   <- h.get[Option[Int]]("checkins")
        _updated    <- h.get[Option[Int]]("updated")
        _address    <- h.get[Option[Int]]("address")
      } yield {
        Place(
          id = _id,
          title = _title,
          latitude = _latitude,
          longitude = _longitude,
          created = _created,
          icon = _icon,
          country = _country,
          city = _city,
          `type` = _type,
          groupId = _groupId,
          groupPhoto = _groupPhoto,
          checkins = _checkins,
          updated = _updated,
          address = _address
        )
      }
    }

  implicit lazy val coordinateEncoder: Encoder[Coordinate] =
    (x: Coordinate) => {
      Json.fromFields(
        List(
          "latitude"  -> x.latitude.asJson,
          "longitude" -> x.longitude.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val coordinateDecoder: Decoder[Coordinate] =
    Decoder.instance { h =>
      for {
        _latitude  <- h.get[Double]("latitude")
        _longitude <- h.get[Double]("longitude")
      } yield {
        Coordinate(latitude = _latitude, longitude = _longitude)
      }
    }

  implicit lazy val geoEncoder: Encoder[Geo] =
    (x: Geo) => {
      Json.fromFields(
        List(
          "type"        -> x.`type`.asJson,
          "coordinates" -> x.coordinates.asJson,
          "place"       -> x.place.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val geoDecoder: Decoder[Geo] =
    Decoder.instance { h =>
      for {
        _type        <- h.get[String]("type")
        _coordinates <- h.get[Coordinate]("coordinates")
        _place       <- h.get[Place]("place")
      } yield {
        Geo(`type` = _type, coordinates = _coordinates, place = _place)
      }
    }

  implicit lazy val commentsthreadEncoder: Encoder[CommentsThread] =
    (x: CommentsThread) => {
      Json.fromFields(
        List(
          "count"             -> x.count.asJson,
          "items"             -> x.items.asJson,
          "can_post"          -> x.canPost.asJson,
          "show_reply_button" -> x.showReplyButton.asJson,
          "groups_can_post"   -> x.groupsCanPost.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val commentsthreadDecoder: Decoder[CommentsThread] =
    Decoder.instance { h =>
      for {
        _count           <- h.get[Int]("count")
        _items           <- h.get[Vector[Comment]]("items")
        _canPost         <- h.get[Boolean]("can_post")
        _showReplyButton <- h.get[Boolean]("show_reply_button")
        _groupsCanPost   <- h.get[Boolean]("groups_can_post")
      } yield {
        CommentsThread(count = _count,
                       items = _items,
                       canPost = _canPost,
                       showReplyButton = _showReplyButton,
                       groupsCanPost = _groupsCanPost)
      }
    }

  implicit lazy val commentEncoder: Encoder[Comment] =
    (x: Comment) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from_id"          -> x.fromId.asJson,
          "date"             -> x.date.asJson,
          "text"             -> x.text.asJson,
          "reply_to_user"    -> x.replyToUser.asJson,
          "reply_to_comment" -> x.replyToComment.asJson,
          "attachments"      -> x.attachments.asJson,
          "parents_stack"    -> x.parentsStack.asJson,
          "thread"           -> x.thread.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val commentDecoder: Decoder[Comment] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _fromId         <- h.get[Int]("from_id")
        _date           <- h.get[Long]("date")
        _text           <- h.get[String]("text")
        _replyToUser    <- h.get[Int]("reply_to_user")
        _replyToComment <- h.get[Int]("reply_to_comment")
        _attachments    <- h.get[Vector[Attachment]]("attachments")
        _parentsStack   <- h.get[Vector[Int]]("parents_stack")
        _thread         <- h.get[CommentsThread]("thread")
      } yield {
        Comment(
          id = _id,
          fromId = _fromId,
          date = _date,
          text = _text,
          replyToUser = _replyToUser,
          replyToComment = _replyToComment,
          attachments = _attachments,
          parentsStack = _parentsStack,
          thread = _thread
        )
      }
    }

  implicit lazy val boardcommentEncoder: Encoder[BoardComment] =
    (x: BoardComment) => {
      Json.fromFields(
        List(
          "id"          -> x.id.asJson,
          "from_id"     -> x.fromId.asJson,
          "date"        -> x.date.asJson,
          "text"        -> x.text.asJson,
          "attachments" -> x.attachments.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val boardcommentDecoder: Decoder[BoardComment] =
    Decoder.instance { h =>
      for {
        _id          <- h.get[Int]("id")
        _fromId      <- h.get[Int]("from_id")
        _date        <- h.get[Long]("date")
        _text        <- h.get[String]("text")
        _attachments <- h.get[Vector[Attachment]]("attachments")
      } yield {
        BoardComment(id = _id,
                     fromId = _fromId,
                     date = _date,
                     text = _text,
                     attachments = _attachments)
      }
    }

  implicit lazy val messageEncoder: Encoder[Message] =
    (x: Message) => {
      Json.fromFields(
        List(
          "id"            -> x.id.asJson,
          "date"          -> x.date.asJson,
          "peer_id"       -> x.peerId.asJson,
          "from_id"       -> x.fromId.asJson,
          "text"          -> x.text.asJson,
          "random_id"     -> x.randomId.asJson,
          "ref"           -> x.ref.asJson,
          "ref_source"    -> x.refSource.asJson,
          "attachments"   -> x.attachments.asJson,
          "important"     -> x.important.asJson,
          "geo"           -> x.geo.asJson,
          "payload"       -> x.payload.asJson,
          "fwd_messages"  -> x.fwdMessages.asJson,
          "reply_message" -> x.replyMessage.asJson,
          "action"        -> x.action.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageDecoder: Decoder[Message] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _date         <- h.get[Long]("date")
        _peerId       <- h.get[Int]("peer_id")
        _fromId       <- h.get[Int]("from_id")
        _text         <- h.get[String]("text")
        _randomId     <- h.get[Int]("random_id")
        _ref          <- h.get[String]("ref")
        _refSource    <- h.get[String]("ref_source")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _important    <- h.get[Boolean]("important")
        _geo          <- h.get[Geo]("geo")
        _payload      <- h.get[String]("payload")
        _fwdMessages  <- h.get[Vector[Message]]("fwd_messages")
        _replyMessage <- h.get[Vector[Message]]("reply_message")
        _action       <- h.get[Action]("action")
      } yield {
        Message(
          id = _id,
          date = _date,
          peerId = _peerId,
          fromId = _fromId,
          text = _text,
          randomId = _randomId,
          ref = _ref,
          refSource = _refSource,
          attachments = _attachments,
          important = _important,
          geo = _geo,
          payload = _payload,
          fwdMessages = _fwdMessages,
          replyMessage = _replyMessage,
          action = _action
        )
      }
    }

  implicit lazy val actionEncoder: Encoder[Action] =
    (x: Action) => {
      Json.fromFields(
        List(
          "type"      -> x.`type`.asJson,
          "member_id" -> x.memberId.asJson,
          "text"      -> x.text.asJson,
          "email"     -> x.email.asJson,
          "photo"     -> x.photo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val actionDecoder: Decoder[Action] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[String]("type")
        _memberId <- h.get[Int]("member_id")
        _text     <- h.get[String]("text")
        _email    <- h.get[String]("email")
        _photo    <- h.get[ActionPhoto]("photo")
      } yield {
        Action(`type` = _type, memberId = _memberId, text = _text, email = _email, photo = _photo)
      }
    }

  implicit lazy val actionphotoEncoder: Encoder[ActionPhoto] =
    (x: ActionPhoto) => {
      Json.fromFields(
        List(
          "photo50"  -> x.photo50.asJson,
          "photo100" -> x.photo100.asJson,
          "photo200" -> x.photo200.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val actionphotoDecoder: Decoder[ActionPhoto] =
    Decoder.instance { h =>
      for {
        _photo50  <- h.get[String]("photo50")
        _photo100 <- h.get[String]("photo100")
        _photo200 <- h.get[String]("photo200")
      } yield {
        ActionPhoto(photo50 = _photo50, photo100 = _photo100, photo200 = _photo200)
      }
    }

  implicit lazy val wallpostEncoder: Encoder[WallPost] =
    (x: WallPost) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "owner_id"       -> x.ownerId.asJson,
          "from_id"        -> x.fromId.asJson,
          "created_by"     -> x.createdBy.asJson,
          "date"           -> x.date.asJson,
          "text"           -> x.text.asJson,
          "reply_owner_id" -> x.replyOwnerId.asJson,
          "reply_post_id"  -> x.replyPostId.asJson,
          "friends_only"   -> x.friendsOnly.asJson,
          "comments"       -> x.comments.asJson,
          "likes"          -> x.likes.asJson,
          "reposts"        -> x.reposts.asJson,
          "views"          -> x.views.asJson,
          "post_type"      -> x.postType.asJson,
          "post_source"    -> x.postSource.asJson,
          "attachments"    -> x.attachments.asJson,
          "geo"            -> x.geo.asJson,
          "signer_id"      -> x.signerId.asJson,
          "copy_history"   -> x.copyHistory.asJson,
          "can_pin"        -> x.canPin.asJson,
          "can_delete"     -> x.canDelete.asJson,
          "can_edit"       -> x.canEdit.asJson,
          "is_pinned"      -> x.isPinned.asJson,
          "marked_as_ads"  -> x.markedAsAds.asJson,
          "is_favorite"    -> x.isFavorite.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val wallpostDecoder: Decoder[WallPost] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _ownerId      <- h.get[Int]("owner_id")
        _fromId       <- h.get[Int]("from_id")
        _createdBy    <- h.get[Int]("created_by")
        _date         <- h.get[Long]("date")
        _text         <- h.get[String]("text")
        _replyOwnerId <- h.get[Int]("reply_owner_id")
        _replyPostId  <- h.get[Int]("reply_post_id")
        _friendsOnly  <- h.get[Option[Int]]("friends_only")
        _comments     <- h.get[WallPostCommentsAnon]("comments")
        _likes        <- h.get[WallPostLikesAnon]("likes")
        _reposts      <- h.get[WallPostReportsAnon]("reposts")
        _views        <- h.get[WallPostViewsAnon]("views")
        _postType     <- h.get[PostType]("post_type")
        _postSource   <- h.get[PostSource]("post_source")
        _attachments  <- h.get[Vector[Attachment]]("attachments")
        _geo          <- h.get[Geo]("geo")
        _signerId     <- h.get[Int]("signer_id")
        _copyHistory  <- h.get[Vector[WallPost]]("copy_history")
        _canPin       <- h.get[Boolean]("can_pin")
        _canDelete    <- h.get[Boolean]("can_delete")
        _canEdit      <- h.get[Boolean]("can_edit")
        _isPinned     <- h.get[Boolean]("is_pinned")
        _markedAsAds  <- h.get[Boolean]("marked_as_ads")
        _isFavorite   <- h.get[Boolean]("is_favorite")
      } yield {
        WallPost(
          id = _id,
          ownerId = _ownerId,
          fromId = _fromId,
          createdBy = _createdBy,
          date = _date,
          text = _text,
          replyOwnerId = _replyOwnerId,
          replyPostId = _replyPostId,
          friendsOnly = _friendsOnly,
          comments = _comments,
          likes = _likes,
          reposts = _reposts,
          views = _views,
          postType = _postType,
          postSource = _postSource,
          attachments = _attachments,
          geo = _geo,
          signerId = _signerId,
          copyHistory = _copyHistory,
          canPin = _canPin,
          canDelete = _canDelete,
          canEdit = _canEdit,
          isPinned = _isPinned,
          markedAsAds = _markedAsAds,
          isFavorite = _isFavorite
        )
      }
    }

  implicit lazy val postsourceEncoder: Encoder[PostSource] =
    (x: PostSource) => {
      Json.fromFields(
        List(
          "type"     -> x.`type`.asJson,
          "platform" -> x.platform.asJson,
          "data"     -> x.data.asJson,
          "url"      -> x.url.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val postsourceDecoder: Decoder[PostSource] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[PostSourceType]("type")
        _platform <- h.get[PlatformType]("platform")
        _data     <- h.get[DataType]("data")
        _url      <- h.get[String]("url")
      } yield {
        PostSource(`type` = _type, platform = _platform, data = _data, url = _url)
      }
    }

  implicit lazy val datatypeEncoder: Encoder[DataType] = {
    case poll: Poll.type => poll.asJson.mapObject(_.add("type", Json.fromString("poll")))
    case profile_photo: ProfilePhoto.type =>
      profile_photo.asJson.mapObject(_.add("type", Json.fromString("profile_photo")))
    case profile_activity: ProfileActivity.type =>
      profile_activity.asJson.mapObject(_.add("type", Json.fromString("profile_activity")))
    case like: Like.type => like.asJson.mapObject(_.add("type", Json.fromString("like")))
    case comments: Comments.type =>
      comments.asJson.mapObject(_.add("type", Json.fromString("comments")))
  }
  implicit lazy val datatypeDecoder: Decoder[DataType] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "poll"             => Decoder[Poll.type]
      case "profile_photo"    => Decoder[ProfilePhoto.type]
      case "profile_activity" => Decoder[ProfileActivity.type]
      case "like"             => Decoder[Like.type]
      case "comments"         => Decoder[Comments.type]
    }
  } yield value

  implicit lazy val profileactivityEncoder: Encoder[ProfileActivity.type] =
    (_: ProfileActivity.type) => ().asJson
  implicit lazy val profileactivityDecoder: Decoder[ProfileActivity.type] = (_: HCursor) =>
    Right(ProfileActivity)
  implicit lazy val profilephotoEncoder: Encoder[ProfilePhoto.type] = (_: ProfilePhoto.type) =>
    ().asJson
  implicit lazy val profilephotoDecoder: Decoder[ProfilePhoto.type] = (_: HCursor) =>
    Right(ProfilePhoto)
  implicit lazy val commentsEncoder: Encoder[Comments.type] = (_: Comments.type) => ().asJson
  implicit lazy val commentsDecoder: Decoder[Comments.type] = (_: HCursor) => Right(Comments)
  implicit lazy val likeEncoder: Encoder[Like.type]         = (_: Like.type) => ().asJson
  implicit lazy val likeDecoder: Decoder[Like.type]         = (_: HCursor) => Right(Like)
  implicit lazy val pollEncoder: Encoder[Poll.type]         = (_: Poll.type) => ().asJson
  implicit lazy val pollDecoder: Decoder[Poll.type]         = (_: HCursor) => Right(Poll)

  implicit lazy val platformtypeEncoder: Encoder[PlatformType] = {
    case iphone: Iphone.type => iphone.asJson.mapObject(_.add("type", Json.fromString("iphone")))
    case android: Android.type =>
      android.asJson.mapObject(_.add("type", Json.fromString("android")))
    case wphone: Wphone.type => wphone.asJson.mapObject(_.add("type", Json.fromString("wphone")))
  }
  implicit lazy val platformtypeDecoder: Decoder[PlatformType] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "iphone"  => Decoder[Iphone.type]
      case "android" => Decoder[Android.type]
      case "wphone"  => Decoder[Wphone.type]
    }
  } yield value

  implicit lazy val androidEncoder: Encoder[Android.type] = (_: Android.type) => ().asJson
  implicit lazy val androidDecoder: Decoder[Android.type] = (_: HCursor) => Right(Android)
  implicit lazy val iphoneEncoder: Encoder[Iphone.type]   = (_: Iphone.type) => ().asJson
  implicit lazy val iphoneDecoder: Decoder[Iphone.type]   = (_: HCursor) => Right(Iphone)
  implicit lazy val wphoneEncoder: Encoder[Wphone.type]   = (_: Wphone.type) => ().asJson
  implicit lazy val wphoneDecoder: Decoder[Wphone.type]   = (_: HCursor) => Right(Wphone)

  implicit lazy val postsourcetypeEncoder: Encoder[PostSourceType] = {
    case sms: Sms.type       => sms.asJson.mapObject(_.add("type", Json.fromString("sms")))
    case widget: Widget.type => widget.asJson.mapObject(_.add("type", Json.fromString("widget")))
    case api: Api.type       => api.asJson.mapObject(_.add("type", Json.fromString("api")))
    case vk: Vk.type         => vk.asJson.mapObject(_.add("type", Json.fromString("vk")))
    case rss: Rss.type       => rss.asJson.mapObject(_.add("type", Json.fromString("rss")))
  }
  implicit lazy val postsourcetypeDecoder: Decoder[PostSourceType] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "sms"    => Decoder[Sms.type]
      case "widget" => Decoder[Widget.type]
      case "api"    => Decoder[Api.type]
      case "vk"     => Decoder[Vk.type]
      case "rss"    => Decoder[Rss.type]
    }
  } yield value

  implicit lazy val vkEncoder: Encoder[Vk.type]         = (_: Vk.type) => ().asJson
  implicit lazy val vkDecoder: Decoder[Vk.type]         = (_: HCursor) => Right(Vk)
  implicit lazy val widgetEncoder: Encoder[Widget.type] = (_: Widget.type) => ().asJson
  implicit lazy val widgetDecoder: Decoder[Widget.type] = (_: HCursor) => Right(Widget)
  implicit lazy val apiEncoder: Encoder[Api.type]       = (_: Api.type) => ().asJson
  implicit lazy val apiDecoder: Decoder[Api.type]       = (_: HCursor) => Right(Api)
  implicit lazy val rssEncoder: Encoder[Rss.type]       = (_: Rss.type) => ().asJson
  implicit lazy val rssDecoder: Decoder[Rss.type]       = (_: HCursor) => Right(Rss)
  implicit lazy val smsEncoder: Encoder[Sms.type]       = (_: Sms.type) => ().asJson
  implicit lazy val smsDecoder: Decoder[Sms.type]       = (_: HCursor) => Right(Sms)

  implicit lazy val posttypeEncoder: Encoder[PostType] = {
    case post: Post.type => post.asJson.mapObject(_.add("type", Json.fromString("post")))
    case suggest: Suggest.type =>
      suggest.asJson.mapObject(_.add("type", Json.fromString("suggest")))
    case reply: Reply.type => reply.asJson.mapObject(_.add("type", Json.fromString("reply")))
    case postpone: Postpone.type =>
      postpone.asJson.mapObject(_.add("type", Json.fromString("postpone")))
    case copy: Copy.type => copy.asJson.mapObject(_.add("type", Json.fromString("copy")))
  }
  implicit lazy val posttypeDecoder: Decoder[PostType] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "post"     => Decoder[Post.type]
      case "suggest"  => Decoder[Suggest.type]
      case "reply"    => Decoder[Reply.type]
      case "postpone" => Decoder[Postpone.type]
      case "copy"     => Decoder[Copy.type]
    }
  } yield value

  implicit lazy val postEncoder: Encoder[Post.type]         = (_: Post.type) => ().asJson
  implicit lazy val postDecoder: Decoder[Post.type]         = (_: HCursor) => Right(Post)
  implicit lazy val copyEncoder: Encoder[Copy.type]         = (_: Copy.type) => ().asJson
  implicit lazy val copyDecoder: Decoder[Copy.type]         = (_: HCursor) => Right(Copy)
  implicit lazy val replyEncoder: Encoder[Reply.type]       = (_: Reply.type) => ().asJson
  implicit lazy val replyDecoder: Decoder[Reply.type]       = (_: HCursor) => Right(Reply)
  implicit lazy val postponeEncoder: Encoder[Postpone.type] = (_: Postpone.type) => ().asJson
  implicit lazy val postponeDecoder: Decoder[Postpone.type] = (_: HCursor) => Right(Postpone)
  implicit lazy val suggestEncoder: Encoder[Suggest.type]   = (_: Suggest.type) => ().asJson
  implicit lazy val suggestDecoder: Decoder[Suggest.type]   = (_: HCursor) => Right(Suggest)
  implicit lazy val wallpostcommentsanonEncoder: Encoder[WallPostCommentsAnon] =
    (x: WallPostCommentsAnon) => {
      Json.fromFields(
        List(
          "count"           -> x.count.asJson,
          "can_post"        -> x.canPost.asJson,
          "groups_can_post" -> x.groupsCanPost.asJson,
          "can_close"       -> x.canClose.asJson,
          "can_open"        -> x.canOpen.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val wallpostcommentsanonDecoder: Decoder[WallPostCommentsAnon] =
    Decoder.instance { h =>
      for {
        _count         <- h.get[Int]("count")
        _canPost       <- h.get[Boolean]("can_post")
        _groupsCanPost <- h.get[Boolean]("groups_can_post")
        _canClose      <- h.get[Boolean]("can_close")
        _canOpen       <- h.get[Boolean]("can_open")
      } yield {
        WallPostCommentsAnon(count = _count,
                             canPost = _canPost,
                             groupsCanPost = _groupsCanPost,
                             canClose = _canClose,
                             canOpen = _canOpen)
      }
    }

  implicit lazy val wallpostlikesanonEncoder: Encoder[WallPostLikesAnon] =
    (x: WallPostLikesAnon) => {
      Json.fromFields(
        List(
          "count"       -> x.count.asJson,
          "user_likes"  -> x.userLikes.asJson,
          "can_like"    -> x.canLike.asJson,
          "can_publish" -> x.canPublish.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val wallpostlikesanonDecoder: Decoder[WallPostLikesAnon] =
    Decoder.instance { h =>
      for {
        _count      <- h.get[Int]("count")
        _userLikes  <- h.get[Boolean]("user_likes")
        _canLike    <- h.get[Boolean]("can_like")
        _canPublish <- h.get[Boolean]("can_publish")
      } yield {
        WallPostLikesAnon(count = _count,
                          userLikes = _userLikes,
                          canLike = _canLike,
                          canPublish = _canPublish)
      }
    }

  implicit lazy val wallpostreportsanonEncoder: Encoder[WallPostReportsAnon] =
    (x: WallPostReportsAnon) => {
      Json.fromFields(
        List(
          "count"         -> x.count.asJson,
          "user_reposted" -> x.userReposted.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val wallpostreportsanonDecoder: Decoder[WallPostReportsAnon] =
    Decoder.instance { h =>
      for {
        _count        <- h.get[Int]("count")
        _userReposted <- h.get[Boolean]("user_reposted")
      } yield {
        WallPostReportsAnon(count = _count, userReposted = _userReposted)
      }
    }

  implicit lazy val wallpostviewsanonEncoder: Encoder[WallPostViewsAnon] =
    (x: WallPostViewsAnon) => {
      Json.fromFields(
        List(
          "count" -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val wallpostviewsanonDecoder: Decoder[WallPostViewsAnon] =
    Decoder.instance { h =>
      for {
        _count <- h.get[Int]("count")
      } yield {
        WallPostViewsAnon(count = _count)
      }
    }

  implicit lazy val userEncoder: Encoder[User] =
    (x: User) => {
      Json.fromFields(
        List(
          "id"                        -> x.id.asJson,
          "first_name"                -> x.firstName.asJson,
          "last_name"                 -> x.lastName.asJson,
          "deactivated"               -> x.deactivated.asJson,
          "is_closed"                 -> x.isClosed.asJson,
          "can_access_closed"         -> x.canAccessClosed.asJson,
          "about"                     -> x.about.asJson,
          "activities"                -> x.activities.asJson,
          "bdate"                     -> x.bdate.asJson,
          "blacklisted"               -> x.blacklisted.asJson,
          "blacklisted_by_me"         -> x.blacklistedByMe.asJson,
          "books"                     -> x.books.asJson,
          "can_post"                  -> x.canPost.asJson,
          "can_see_all_posts"         -> x.canSeeAllPosts.asJson,
          "can_see_audio"             -> x.canSeeAudio.asJson,
          "can_send_friend_request"   -> x.canSendFriendRequest.asJson,
          "can_write_private_message" -> x.can_write_private_message.asJson,
          "career"                    -> x.career.asJson,
          "city"                      -> x.city.asJson,
          "common_count"              -> x.commonCount.asJson,
          "skype"                     -> x.skype.asJson,
          "facebook"                  -> x.facebook.asJson,
          "twitter"                   -> x.twitter.asJson,
          "livejournal"               -> x.livejournal.asJson,
          "instagram"                 -> x.instagram.asJson,
          "contacts"                  -> x.contacts.asJson,
          "counters"                  -> x.counters.asJson,
          "country"                   -> x.country.asJson,
          "crop_photo"                -> x.cropPhoto.asJson,
          "domain"                    -> x.domain.asJson,
          "education"                 -> x.education.asJson,
          "first_name_nom"            -> x.firstNameNom.asJson,
          "first_name_gen"            -> x.firstNameGen.asJson,
          "first_name_dat"            -> x.firstNameDat.asJson,
          "first_name_acc"            -> x.firstNameAcc.asJson,
          "first_name_ins"            -> x.firstNameIns.asJson,
          "first_name_abl"            -> x.firstNameAbl.asJson,
          "followers_count"           -> x.followersCount.asJson,
          "friend_status"             -> x.friendStatus.asJson,
          "games"                     -> x.games.asJson,
          "has_mobile"                -> x.hasMobile.asJson,
          "has_photo"                 -> x.hasPhoto.asJson,
          "home_town"                 -> x.homeTown.asJson,
          "interests"                 -> x.interests.asJson,
          "is_favorite"               -> x.isFavorite.asJson,
          "is_friend"                 -> x.isFriend.asJson,
          "is_hidden_from_feed"       -> x.isHiddenFromFeed.asJson,
          "last_name_nom"             -> x.lastNameNom.asJson,
          "last_name_gen"             -> x.lastNameGen.asJson,
          "last_name_dat"             -> x.lastNameDat.asJson,
          "last_name_acc"             -> x.lastNameAcc.asJson,
          "last_name_ins"             -> x.lastNameIns.asJson,
          "last_name_abl"             -> x.lastNameAbl.asJson,
          "last_seen"                 -> x.lastSeen.asJson,
          "lists"                     -> x.lists.asJson,
          "maiden_name"               -> x.maidenName.asJson,
          "military"                  -> x.military.asJson,
          "movies"                    -> x.movies.asJson,
          "music"                     -> x.music.asJson,
          "nickname"                  -> x.nickname.asJson,
          "occupation"                -> x.occupation.asJson,
          "online"                    -> x.online.asJson,
          "online_mobile"             -> x.onlineMobile.asJson,
          "online_app"                -> x.onlineApp.asJson,
          "personal"                  -> x.personal.asJson,
          "photo50"                   -> x.photo50.asJson,
          "photo100"                  -> x.photo100.asJson,
          "photo200orig"              -> x.photo200orig.asJson,
          "photo200"                  -> x.photo200.asJson,
          "photo400orig"              -> x.photo400orig.asJson,
          "photo_id"                  -> x.photoId.asJson,
          "photo_max"                 -> x.photoMax.asJson,
          "photo_max_orig"            -> x.photoMaxOrig.asJson,
          "quotes"                    -> x.quotes.asJson,
          "relatives"                 -> x.relatives.asJson,
          "relation"                  -> x.relation.asJson,
          "schools"                   -> x.schools.asJson,
          "screen_name"               -> x.screenName.asJson,
          "sex"                       -> x.sex.asJson,
          "site"                      -> x.site.asJson,
          "status"                    -> x.status.asJson,
          "timezone"                  -> x.timezone.asJson,
          "trending"                  -> x.trending.asJson,
          "tv"                        -> x.tv.asJson,
          "universities"              -> x.universities.asJson,
          "verified"                  -> x.verified.asJson,
          "wall_default"              -> x.wallDefault.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userDecoder: Decoder[User] =
    Decoder.instance { h =>
      for {
        _id                        <- h.get[Int]("id")
        _firstName                 <- h.get[String]("first_name")
        _lastName                  <- h.get[String]("last_name")
        _deactivated               <- h.get[String]("deactivated")
        _isClosed                  <- h.get[Boolean]("is_closed")
        _canAccessClosed           <- h.get[Boolean]("can_access_closed")
        _about                     <- h.get[Option[String]]("about")
        _activities                <- h.get[Option[String]]("activities")
        _bdate                     <- h.get[Option[String]]("bdate")
        _blacklisted               <- h.get[Option[Boolean]]("blacklisted")
        _blacklistedByMe           <- h.get[Option[Boolean]]("blacklisted_by_me")
        _books                     <- h.get[Option[String]]("books")
        _canPost                   <- h.get[Option[Boolean]]("can_post")
        _canSeeAllPosts            <- h.get[Option[Boolean]]("can_see_all_posts")
        _canSeeAudio               <- h.get[Option[Boolean]]("can_see_audio")
        _canSendFriendRequest      <- h.get[Option[Boolean]]("can_send_friend_request")
        _can_write_private_message <- h.get[Option[Boolean]]("can_write_private_message")
        _career                    <- h.get[Option[Career]]("career")
        _city                      <- h.get[Option[City]]("city")
        _commonCount               <- h.get[Option[Int]]("common_count")
        _skype                     <- h.get[Option[String]]("skype")
        _facebook                  <- h.get[Option[String]]("facebook")
        _twitter                   <- h.get[Option[String]]("twitter")
        _livejournal               <- h.get[Option[String]]("livejournal")
        _instagram                 <- h.get[Option[String]]("instagram")
        _contacts                  <- h.get[Option[Contacts]]("contacts")
        _counters                  <- h.get[Option[Counters]]("counters")
        _country                   <- h.get[Option[Country]]("country")
        _cropPhoto                 <- h.get[Option[CropPhoto]]("crop_photo")
        _domain                    <- h.get[Option[String]]("domain")
        _education                 <- h.get[Option[Education]]("education")
        _firstNameNom              <- h.get[Option[String]]("first_name_nom")
        _firstNameGen              <- h.get[Option[String]]("first_name_gen")
        _firstNameDat              <- h.get[Option[String]]("first_name_dat")
        _firstNameAcc              <- h.get[Option[String]]("first_name_acc")
        _firstNameIns              <- h.get[Option[String]]("first_name_ins")
        _firstNameAbl              <- h.get[Option[String]]("first_name_abl")
        _followersCount            <- h.get[Option[Int]]("followers_count")
        _friendStatus              <- h.get[Option[Int]]("friend_status")
        _games                     <- h.get[Option[String]]("games")
        _hasMobile                 <- h.get[Option[Boolean]]("has_mobile")
        _hasPhoto                  <- h.get[Option[Boolean]]("has_photo")
        _homeTown                  <- h.get[Option[String]]("home_town")
        _interests                 <- h.get[Option[String]]("interests")
        _isFavorite                <- h.get[Option[Boolean]]("is_favorite")
        _isFriend                  <- h.get[Option[Boolean]]("is_friend")
        _isHiddenFromFeed          <- h.get[Option[Boolean]]("is_hidden_from_feed")
        _lastNameNom               <- h.get[Option[String]]("last_name_nom")
        _lastNameGen               <- h.get[Option[String]]("last_name_gen")
        _lastNameDat               <- h.get[Option[String]]("last_name_dat")
        _lastNameAcc               <- h.get[Option[String]]("last_name_acc")
        _lastNameIns               <- h.get[Option[String]]("last_name_ins")
        _lastNameAbl               <- h.get[Option[String]]("last_name_abl")
        _lastSeen                  <- h.get[Option[LastSeen]]("last_seen")
        _lists                     <- h.get[Option[String]]("lists")
        _maidenName                <- h.get[Option[String]]("maiden_name")
        _military                  <- h.get[Option[Military]]("military")
        _movies                    <- h.get[Option[String]]("movies")
        _music                     <- h.get[Option[String]]("music")
        _nickname                  <- h.get[Option[String]]("nickname")
        _occupation                <- h.get[Option[Occupation]]("occupation")
        _online                    <- h.get[Option[Boolean]]("online")
        _onlineMobile              <- h.get[Option[Boolean]]("online_mobile")
        _onlineApp                 <- h.get[Option[String]]("online_app")
        _personal                  <- h.get[Option[Personal]]("personal")
        _photo50                   <- h.get[Option[String]]("photo50")
        _photo100                  <- h.get[Option[String]]("photo100")
        _photo200orig              <- h.get[Option[String]]("photo200orig")
        _photo200                  <- h.get[Option[String]]("photo200")
        _photo400orig              <- h.get[Option[String]]("photo400orig")
        _photoId                   <- h.get[Option[String]]("photo_id")
        _photoMax                  <- h.get[Option[String]]("photo_max")
        _photoMaxOrig              <- h.get[Option[String]]("photo_max_orig")
        _quotes                    <- h.get[Option[String]]("quotes")
        _relatives                 <- h.get[Option[Relative]]("relatives")
        _relation                  <- h.get[Option[Int]]("relation")
        _schools                   <- h.get[Option[School]]("schools")
        _screenName                <- h.get[Option[String]]("screen_name")
        _sex                       <- h.get[Option[Int]]("sex")
        _site                      <- h.get[Option[String]]("site")
        _status                    <- h.get[Option[String]]("status")
        _timezone                  <- h.get[Option[Int]]("timezone")
        _trending                  <- h.get[Option[Boolean]]("trending")
        _tv                        <- h.get[Option[String]]("tv")
        _universities              <- h.get[Vector[University]]("universities")
        _verified                  <- h.get[Option[Boolean]]("verified")
        _wallDefault               <- h.get[Option[String]]("wall_default")
      } yield {
        User(
          id = _id,
          firstName = _firstName,
          lastName = _lastName,
          deactivated = _deactivated,
          isClosed = _isClosed,
          canAccessClosed = _canAccessClosed,
          about = _about,
          activities = _activities,
          bdate = _bdate,
          blacklisted = _blacklisted,
          blacklistedByMe = _blacklistedByMe,
          books = _books,
          canPost = _canPost,
          canSeeAllPosts = _canSeeAllPosts,
          canSeeAudio = _canSeeAudio,
          canSendFriendRequest = _canSendFriendRequest,
          can_write_private_message = _can_write_private_message,
          career = _career,
          city = _city,
          commonCount = _commonCount,
          skype = _skype,
          facebook = _facebook,
          twitter = _twitter,
          livejournal = _livejournal,
          instagram = _instagram,
          contacts = _contacts,
          counters = _counters,
          country = _country,
          cropPhoto = _cropPhoto,
          domain = _domain,
          education = _education,
          firstNameNom = _firstNameNom,
          firstNameGen = _firstNameGen,
          firstNameDat = _firstNameDat,
          firstNameAcc = _firstNameAcc,
          firstNameIns = _firstNameIns,
          firstNameAbl = _firstNameAbl,
          followersCount = _followersCount,
          friendStatus = _friendStatus,
          games = _games,
          hasMobile = _hasMobile,
          hasPhoto = _hasPhoto,
          homeTown = _homeTown,
          interests = _interests,
          isFavorite = _isFavorite,
          isFriend = _isFriend,
          isHiddenFromFeed = _isHiddenFromFeed,
          lastNameNom = _lastNameNom,
          lastNameGen = _lastNameGen,
          lastNameDat = _lastNameDat,
          lastNameAcc = _lastNameAcc,
          lastNameIns = _lastNameIns,
          lastNameAbl = _lastNameAbl,
          lastSeen = _lastSeen,
          lists = _lists,
          maidenName = _maidenName,
          military = _military,
          movies = _movies,
          music = _music,
          nickname = _nickname,
          occupation = _occupation,
          online = _online,
          onlineMobile = _onlineMobile,
          onlineApp = _onlineApp,
          personal = _personal,
          photo50 = _photo50,
          photo100 = _photo100,
          photo200orig = _photo200orig,
          photo200 = _photo200,
          photo400orig = _photo400orig,
          photoId = _photoId,
          photoMax = _photoMax,
          photoMaxOrig = _photoMaxOrig,
          quotes = _quotes,
          relatives = _relatives,
          relation = _relation,
          schools = _schools,
          screenName = _screenName,
          sex = _sex,
          site = _site,
          status = _status,
          timezone = _timezone,
          trending = _trending,
          tv = _tv,
          universities = _universities,
          verified = _verified,
          wallDefault = _wallDefault
        )
      }
    }

  implicit lazy val careerEncoder: Encoder[Career] =
    (x: Career) => {
      Json.fromFields(
        List(
          "group_id"   -> x.groupId.asJson,
          "company"    -> x.company.asJson,
          "country_id" -> x.countryId.asJson,
          "city_id"    -> x.cityId.asJson,
          "city_name"  -> x.cityName.asJson,
          "from"       -> x.from.asJson,
          "until"      -> x.until.asJson,
          "position"   -> x.position.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val careerDecoder: Decoder[Career] =
    Decoder.instance { h =>
      for {
        _groupId   <- h.get[Int]("group_id")
        _company   <- h.get[String]("company")
        _countryId <- h.get[Int]("country_id")
        _cityId    <- h.get[Int]("city_id")
        _cityName  <- h.get[String]("city_name")
        _from      <- h.get[Int]("from")
        _until     <- h.get[Int]("until")
        _position  <- h.get[String]("position")
      } yield {
        Career(groupId = _groupId,
               company = _company,
               countryId = _countryId,
               cityId = _cityId,
               cityName = _cityName,
               from = _from,
               until = _until,
               position = _position)
      }
    }

  implicit lazy val cityEncoder: Encoder[City] =
    (x: City) => {
      Json.fromFields(
        List(
          "id"    -> x.id.asJson,
          "title" -> x.title.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val cityDecoder: Decoder[City] =
    Decoder.instance { h =>
      for {
        _id    <- h.get[Int]("id")
        _title <- h.get[String]("title")
      } yield {
        City(id = _id, title = _title)
      }
    }

  implicit lazy val contactsEncoder: Encoder[Contacts] =
    (x: Contacts) => {
      Json.fromFields(
        List(
          "mobile_phone" -> x.mobilePhone.asJson,
          "home_phone"   -> x.homePhone.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val contactsDecoder: Decoder[Contacts] =
    Decoder.instance { h =>
      for {
        _mobilePhone <- h.get[String]("mobile_phone")
        _homePhone   <- h.get[String]("home_phone")
      } yield {
        Contacts(mobilePhone = _mobilePhone, homePhone = _homePhone)
      }
    }

  implicit lazy val countersEncoder: Encoder[Counters] =
    (x: Counters) => {
      Json.fromFields(
        List(
          "albums"         -> x.albums.asJson,
          "videos"         -> x.videos.asJson,
          "audios"         -> x.audios.asJson,
          "photos"         -> x.photos.asJson,
          "notes"          -> x.notes.asJson,
          "friends"        -> x.friends.asJson,
          "groups"         -> x.groups.asJson,
          "online_friends" -> x.onlineFriends.asJson,
          "mutual_friends" -> x.mutualFriends.asJson,
          "user_videos"    -> x.userVideos.asJson,
          "followers"      -> x.followers.asJson,
          "pages"          -> x.pages.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val countersDecoder: Decoder[Counters] =
    Decoder.instance { h =>
      for {
        _albums        <- h.get[Integer]("albums")
        _videos        <- h.get[Integer]("videos")
        _audios        <- h.get[Integer]("audios")
        _photos        <- h.get[Integer]("photos")
        _notes         <- h.get[Integer]("notes")
        _friends       <- h.get[Integer]("friends")
        _groups        <- h.get[Integer]("groups")
        _onlineFriends <- h.get[Integer]("online_friends")
        _mutualFriends <- h.get[Integer]("mutual_friends")
        _userVideos    <- h.get[Integer]("user_videos")
        _followers     <- h.get[Integer]("followers")
        _pages         <- h.get[Integer]("pages")
      } yield {
        Counters(
          albums = _albums,
          videos = _videos,
          audios = _audios,
          photos = _photos,
          notes = _notes,
          friends = _friends,
          groups = _groups,
          onlineFriends = _onlineFriends,
          mutualFriends = _mutualFriends,
          userVideos = _userVideos,
          followers = _followers,
          pages = _pages
        )
      }
    }

  implicit lazy val countryEncoder: Encoder[Country] =
    (x: Country) => {
      Json.fromFields(
        List(
          "id"    -> x.id.asJson,
          "title" -> x.title.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val countryDecoder: Decoder[Country] =
    Decoder.instance { h =>
      for {
        _id    <- h.get[Int]("id")
        _title <- h.get[String]("title")
      } yield {
        Country(id = _id, title = _title)
      }
    }

  implicit lazy val cropphotoEncoder: Encoder[CropPhoto] =
    (x: CropPhoto) => {
      Json.fromFields(
        List(
          "photo" -> x.photo.asJson,
          "crop"  -> x.crop.asJson,
          "rect"  -> x.rect.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val cropphotoDecoder: Decoder[CropPhoto] =
    Decoder.instance { h =>
      for {
        _photo <- h.get[Photo]("photo")
        _crop  <- h.get[Crop]("crop")
        _rect  <- h.get[Crop]("rect")
      } yield {
        CropPhoto(photo = _photo, crop = _crop, rect = _rect)
      }
    }

  implicit lazy val cropEncoder: Encoder[Crop] =
    (x: Crop) => {
      Json.fromFields(
        List(
          "x"  -> x.x.asJson,
          "y"  -> x.y.asJson,
          "x2" -> x.x2.asJson,
          "y2" -> x.y2.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val cropDecoder: Decoder[Crop] =
    Decoder.instance { h =>
      for {
        _x  <- h.get[Integer]("x")
        _y  <- h.get[Integer]("y")
        _x2 <- h.get[Integer]("x2")
        _y2 <- h.get[Integer]("y2")
      } yield {
        Crop(x = _x, y = _y, x2 = _x2, y2 = _y2)
      }
    }

  implicit lazy val educationEncoder: Encoder[Education] =
    (x: Education) => {
      Json.fromFields(
        List(
          "university"      -> x.university.asJson,
          "university_name" -> x.universityName.asJson,
          "faculty"         -> x.faculty.asJson,
          "faculty_name"    -> x.facultyName.asJson,
          "graduation"      -> x.graduation.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val educationDecoder: Decoder[Education] =
    Decoder.instance { h =>
      for {
        _university     <- h.get[Integer]("university")
        _universityName <- h.get[String]("university_name")
        _faculty        <- h.get[Integer]("faculty")
        _facultyName    <- h.get[String]("faculty_name")
        _graduation     <- h.get[Integer]("graduation")
      } yield {
        Education(university = _university,
                  universityName = _universityName,
                  faculty = _faculty,
                  facultyName = _facultyName,
                  graduation = _graduation)
      }
    }

  implicit lazy val lastseenEncoder: Encoder[LastSeen] =
    (x: LastSeen) => {
      Json.fromFields(
        List(
          "time"     -> x.time.asJson,
          "platform" -> x.platform.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val lastseenDecoder: Decoder[LastSeen] =
    Decoder.instance { h =>
      for {
        _time     <- h.get[Integer]("time")
        _platform <- h.get[Integer]("platform")
      } yield {
        LastSeen(time = _time, platform = _platform)
      }
    }

  implicit lazy val militaryEncoder: Encoder[Military] =
    (x: Military) => {
      Json.fromFields(
        List(
          "unit"       -> x.unit.asJson,
          "unit_id"    -> x.unitId.asJson,
          "country_id" -> x.countryId.asJson,
          "from"       -> x.from.asJson,
          "until"      -> x.until.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val militaryDecoder: Decoder[Military] =
    Decoder.instance { h =>
      for {
        _unit      <- h.get[String]("unit")
        _unitId    <- h.get[Integer]("unit_id")
        _countryId <- h.get[Integer]("country_id")
        _from      <- h.get[Integer]("from")
        _until     <- h.get[Integer]("until")
      } yield {
        Military(unit = _unit,
                 unitId = _unitId,
                 countryId = _countryId,
                 from = _from,
                 until = _until)
      }
    }

  implicit lazy val occupationEncoder: Encoder[Occupation] =
    (x: Occupation) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "id"   -> x.id.asJson,
          "name" -> x.name.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val occupationDecoder: Decoder[Occupation] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _id   <- h.get[Int]("id")
        _name <- h.get[String]("name")
      } yield {
        Occupation(`type` = _type, id = _id, name = _name)
      }
    }

  implicit lazy val personalEncoder: Encoder[Personal] =
    (x: Personal) => {
      Json.fromFields(
        List(
          "political"   -> x.political.asJson,
          "langs"       -> x.langs.asJson,
          "religion"    -> x.religion.asJson,
          "inspired_by" -> x.inspiredBy.asJson,
          "people_main" -> x.peopleMain.asJson,
          "life_main"   -> x.lifeMain.asJson,
          "smoking"     -> x.smoking.asJson,
          "alcohol"     -> x.alcohol.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val personalDecoder: Decoder[Personal] =
    Decoder.instance { h =>
      for {
        _political  <- h.get[Int]("political")
        _langs      <- h.get[Vector[String]]("langs")
        _religion   <- h.get[String]("religion")
        _inspiredBy <- h.get[String]("inspired_by")
        _peopleMain <- h.get[Int]("people_main")
        _lifeMain   <- h.get[Int]("life_main")
        _smoking    <- h.get[Int]("smoking")
        _alcohol    <- h.get[Int]("alcohol")
      } yield {
        Personal(
          political = _political,
          langs = _langs,
          religion = _religion,
          inspiredBy = _inspiredBy,
          peopleMain = _peopleMain,
          lifeMain = _lifeMain,
          smoking = _smoking,
          alcohol = _alcohol
        )
      }
    }

  implicit lazy val relativeEncoder: Encoder[Relative] =
    (x: Relative) => {
      Json.fromFields(
        List(
          "id"   -> x.id.asJson,
          "name" -> x.name.asJson,
          "type" -> x.`type`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val relativeDecoder: Decoder[Relative] =
    Decoder.instance { h =>
      for {
        _id   <- h.get[Int]("id")
        _name <- h.get[String]("name")
        _type <- h.get[String]("type")
      } yield {
        Relative(id = _id, name = _name, `type` = _type)
      }
    }

  implicit lazy val schoolEncoder: Encoder[School] =
    (x: School) => {
      Json.fromFields(
        List(
          "id"             -> x.id.asJson,
          "country"        -> x.country.asJson,
          "city"           -> x.city.asJson,
          "name"           -> x.name.asJson,
          "year_from"      -> x.yearFrom.asJson,
          "year_to"        -> x.yearTo.asJson,
          "year_graduated" -> x.yearGraduated.asJson,
          "class"          -> x.`class`.asJson,
          "speciality"     -> x.speciality.asJson,
          "type"           -> x.`type`.asJson,
          "type_str"       -> x.typeStr.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val schoolDecoder: Decoder[School] =
    Decoder.instance { h =>
      for {
        _id            <- h.get[Integer]("id")
        _country       <- h.get[Integer]("country")
        _city          <- h.get[Integer]("city")
        _name          <- h.get[String]("name")
        _yearFrom      <- h.get[Integer]("year_from")
        _yearTo        <- h.get[Integer]("year_to")
        _yearGraduated <- h.get[Integer]("year_graduated")
        _class         <- h.get[String]("class")
        _speciality    <- h.get[String]("speciality")
        _type          <- h.get[Integer]("type")
        _typeStr       <- h.get[String]("type_str")
      } yield {
        School(
          id = _id,
          country = _country,
          city = _city,
          name = _name,
          yearFrom = _yearFrom,
          yearTo = _yearTo,
          yearGraduated = _yearGraduated,
          `class` = _class,
          speciality = _speciality,
          `type` = _type,
          typeStr = _typeStr
        )
      }
    }

  implicit lazy val universityEncoder: Encoder[University] =
    (x: University) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "country"          -> x.country.asJson,
          "city"             -> x.city.asJson,
          "name"             -> x.name.asJson,
          "faculty"          -> x.faculty.asJson,
          "faculty_name"     -> x.facultyName.asJson,
          "chair"            -> x.chair.asJson,
          "chair_name"       -> x.chairName.asJson,
          "graduation"       -> x.graduation.asJson,
          "education_form"   -> x.educationForm.asJson,
          "education_status" -> x.educationStatus.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val universityDecoder: Decoder[University] =
    Decoder.instance { h =>
      for {
        _id              <- h.get[Integer]("id")
        _country         <- h.get[Integer]("country")
        _city            <- h.get[Integer]("city")
        _name            <- h.get[String]("name")
        _faculty         <- h.get[Integer]("faculty")
        _facultyName     <- h.get[String]("faculty_name")
        _chair           <- h.get[Integer]("chair")
        _chairName       <- h.get[String]("chair_name")
        _graduation      <- h.get[Integer]("graduation")
        _educationForm   <- h.get[String]("education_form")
        _educationStatus <- h.get[String]("education_status")
      } yield {
        University(
          id = _id,
          country = _country,
          city = _city,
          name = _name,
          faculty = _faculty,
          facultyName = _facultyName,
          chair = _chair,
          chairName = _chairName,
          graduation = _graduation,
          educationForm = _educationForm,
          educationStatus = _educationStatus
        )
      }
    }

  implicit lazy val conversationEncoder: Encoder[Conversation] =
    (x: Conversation) => {
      Json.fromFields(
        List(
          "peer"          -> x.peer.asJson,
          "in_read"       -> x.inRead.asJson,
          "out_read"      -> x.outRead.asJson,
          "unread_count"  -> x.unreadCount.asJson,
          "important"     -> x.important.asJson,
          "unanswered"    -> x.unanswered.asJson,
          "push_settings" -> x.pushSettings.asJson,
          "can_write"     -> x.canWrite.asJson,
          "chat_settings" -> x.chatSettings.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val conversationDecoder: Decoder[Conversation] =
    Decoder.instance { h =>
      for {
        _peer         <- h.get[Peer]("peer")
        _inRead       <- h.get[Int]("in_read")
        _outRead      <- h.get[Int]("out_read")
        _unreadCount  <- h.get[Int]("unread_count")
        _important    <- h.get[Boolean]("important")
        _unanswered   <- h.get[Boolean]("unanswered")
        _pushSettings <- h.get[PushSettings]("push_settings")
        _canWrite     <- h.get[CanWrite]("can_write")
        _chatSettings <- h.get[ChatSettings]("chat_settings")
      } yield {
        Conversation(
          peer = _peer,
          inRead = _inRead,
          outRead = _outRead,
          unreadCount = _unreadCount,
          important = _important,
          unanswered = _unanswered,
          pushSettings = _pushSettings,
          canWrite = _canWrite,
          chatSettings = _chatSettings
        )
      }
    }

  implicit lazy val peerEncoder: Encoder[Peer] =
    (x: Peer) => {
      Json.fromFields(
        List(
          "id"       -> x.id.asJson,
          "type"     -> x.`type`.asJson,
          "local_id" -> x.localId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val peerDecoder: Decoder[Peer] =
    Decoder.instance { h =>
      for {
        _id      <- h.get[Int]("id")
        _type    <- h.get[String]("type")
        _localId <- h.get[Int]("local_id")
      } yield {
        Peer(id = _id, `type` = _type, localId = _localId)
      }
    }

  implicit lazy val pushsettingsEncoder: Encoder[PushSettings] =
    (x: PushSettings) => {
      Json.fromFields(
        List(
          "disabled_until"   -> x.disabledUntil.asJson,
          "disabled_forever" -> x.disabledForever.asJson,
          "no_sound"         -> x.noSound.asJson,
          "sound"            -> x.sound.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pushsettingsDecoder: Decoder[PushSettings] =
    Decoder.instance { h =>
      for {
        _disabledUntil   <- h.get[Int]("disabled_until")
        _disabledForever <- h.get[Boolean]("disabled_forever")
        _noSound         <- h.get[Boolean]("no_sound")
        _sound           <- h.get[Boolean]("sound")
      } yield {
        PushSettings(disabledUntil = _disabledUntil,
                     disabledForever = _disabledForever,
                     noSound = _noSound,
                     sound = _sound)
      }
    }

  implicit lazy val canwriteEncoder: Encoder[CanWrite] =
    (x: CanWrite) => {
      Json.fromFields(
        List(
          "allowed" -> x.allowed.asJson,
          "reason"  -> x.reason.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val canwriteDecoder: Decoder[CanWrite] =
    Decoder.instance { h =>
      for {
        _allowed <- h.get[Boolean]("allowed")
        _reason  <- h.get[Int]("reason")
      } yield {
        CanWrite(allowed = _allowed, reason = _reason)
      }
    }

  implicit lazy val chatsettingsEncoder: Encoder[ChatSettings] =
    (x: ChatSettings) => {
      Json.fromFields(
        List(
          "members_count"    -> x.membersCount.asJson,
          "title"            -> x.title.asJson,
          "pinned_message"   -> x.pinnedMessage.asJson,
          "state"            -> x.state.asJson,
          "photo"            -> x.photo.asJson,
          "active_ids"       -> x.activeIds.asJson,
          "is_group_channel" -> x.isGroupChannel.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatsettingsDecoder: Decoder[ChatSettings] =
    Decoder.instance { h =>
      for {
        _membersCount   <- h.get[Int]("members_count")
        _title          <- h.get[String]("title")
        _pinnedMessage  <- h.get[PinnedMessage]("pinned_message")
        _state          <- h.get[String]("state")
        _photo          <- h.get[ActionPhoto]("photo")
        _activeIds      <- h.get[Vector[Int]]("active_ids")
        _isGroupChannel <- h.get[Boolean]("is_group_channel")
      } yield {
        ChatSettings(membersCount = _membersCount,
                     title = _title,
                     pinnedMessage = _pinnedMessage,
                     state = _state,
                     photo = _photo,
                     activeIds = _activeIds,
                     isGroupChannel = _isGroupChannel)
      }
    }

  implicit lazy val pinnedmessageEncoder: Encoder[PinnedMessage] =
    (x: PinnedMessage) => {
      Json.fromFields(
        List(
          "id"           -> x.id.asJson,
          "date"         -> x.date.asJson,
          "from_id"      -> x.fromId.asJson,
          "text"         -> x.text.asJson,
          "attachments"  -> x.attachments.asJson,
          "geo"          -> x.geo.asJson,
          "fwd_messages" -> x.fwdMessages.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pinnedmessageDecoder: Decoder[PinnedMessage] =
    Decoder.instance { h =>
      for {
        _id          <- h.get[Int]("id")
        _date        <- h.get[Int]("date")
        _fromId      <- h.get[Int]("from_id")
        _text        <- h.get[String]("text")
        _attachments <- h.get[String]("attachments")
        _geo         <- h.get[Geo]("geo")
        _fwdMessages <- h.get[Vector[Message]]("fwd_messages")
      } yield {
        PinnedMessage(id = _id,
                      date = _date,
                      fromId = _fromId,
                      text = _text,
                      attachments = _attachments,
                      geo = _geo,
                      fwdMessages = _fwdMessages)
      }
    }

  implicit lazy val chatEncoder: Encoder[Chat] =
    (x: Chat) => {
      Json.fromFields(
        List(
          "id"            -> x.id.asJson,
          "type"          -> x.`type`.asJson,
          "title"         -> x.title.asJson,
          "admin_id"      -> x.adminId.asJson,
          "users"         -> x.users.asJson,
          "push_settings" -> x.pushSettings.asJson,
          "photo50"       -> x.photo50.asJson,
          "photo100"      -> x.photo100.asJson,
          "photo200"      -> x.photo200.asJson,
          "left"          -> x.left.asJson,
          "kicked"        -> x.kicked.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatDecoder: Decoder[Chat] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _type         <- h.get[String]("type")
        _title        <- h.get[String]("title")
        _adminId      <- h.get[Int]("admin_id")
        _users        <- h.get[Vector[User]]("users")
        _pushSettings <- h.get[PushSettings]("push_settings")
        _photo50      <- h.get[String]("photo50")
        _photo100     <- h.get[String]("photo100")
        _photo200     <- h.get[String]("photo200")
        _left         <- h.get[Boolean]("left")
        _kicked       <- h.get[Boolean]("kicked")
      } yield {
        Chat(
          id = _id,
          `type` = _type,
          title = _title,
          adminId = _adminId,
          users = _users,
          pushSettings = _pushSettings,
          photo50 = _photo50,
          photo100 = _photo100,
          photo200 = _photo200,
          left = _left,
          kicked = _kicked
        )
      }
    }

  implicit lazy val noteEncoder: Encoder[Note] =
    (x: Note) => {
      Json.fromFields(
        List(
          "id"            -> x.id.asJson,
          "owner_id"      -> x.ownerId.asJson,
          "title"         -> x.title.asJson,
          "text"          -> x.text.asJson,
          "date"          -> x.date.asJson,
          "comments"      -> x.comments.asJson,
          "read_comments" -> x.readComments.asJson,
          "view_url"      -> x.viewUrl.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val noteDecoder: Decoder[Note] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _ownerId      <- h.get[Int]("owner_id")
        _title        <- h.get[String]("title")
        _text         <- h.get[String]("text")
        _date         <- h.get[Int]("date")
        _comments     <- h.get[Int]("comments")
        _readComments <- h.get[Int]("read_comments")
        _viewUrl      <- h.get[String]("view_url")
      } yield {
        Note(id = _id,
             ownerId = _ownerId,
             title = _title,
             text = _text,
             date = _date,
             comments = _comments,
             readComments = _readComments,
             viewUrl = _viewUrl)
      }
    }

  implicit lazy val wikipageEncoder: Encoder[WikiPage] =
    (x: WikiPage) => {
      Json.fromFields(
        List(
          "id"                           -> x.id.asJson,
          "group_id"                     -> x.groupId.asJson,
          "creator_id"                   -> x.creatorId.asJson,
          "title"                        -> x.title.asJson,
          "current_user_can_edit"        -> x.currentUserCanEdit.asJson,
          "current_user_can_edit_access" -> x.currentUserCanEditAccess.asJson,
          "who_can_view"                 -> x.whoCanView.asJson,
          "who_can_edit"                 -> x.whoCanEdit.asJson,
          "edited"                       -> x.edited.asJson,
          "created"                      -> x.created.asJson,
          "editor_id"                    -> x.editorId.asJson,
          "views"                        -> x.views.asJson,
          "parent"                       -> x.parent.asJson,
          "parent2"                      -> x.parent2.asJson,
          "source"                       -> x.source.asJson,
          "html"                         -> x.html.asJson,
          "view_url"                     -> x.viewUrl.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val wikipageDecoder: Decoder[WikiPage] =
    Decoder.instance { h =>
      for {
        _id                       <- h.get[Int]("id")
        _groupId                  <- h.get[Int]("group_id")
        _creatorId                <- h.get[Int]("creator_id")
        _title                    <- h.get[String]("title")
        _currentUserCanEdit       <- h.get[Boolean]("current_user_can_edit")
        _currentUserCanEditAccess <- h.get[Boolean]("current_user_can_edit_access")
        _whoCanView               <- h.get[Int]("who_can_view")
        _whoCanEdit               <- h.get[Int]("who_can_edit")
        _edited                   <- h.get[Int]("edited")
        _created                  <- h.get[Int]("created")
        _editorId                 <- h.get[Int]("editor_id")
        _views                    <- h.get[Int]("views")
        _parent                   <- h.get[String]("parent")
        _parent2                  <- h.get[String]("parent2")
        _source                   <- h.get[String]("source")
        _html                     <- h.get[String]("html")
        _viewUrl                  <- h.get[String]("view_url")
      } yield {
        WikiPage(
          id = _id,
          groupId = _groupId,
          creatorId = _creatorId,
          title = _title,
          currentUserCanEdit = _currentUserCanEdit,
          currentUserCanEditAccess = _currentUserCanEditAccess,
          whoCanView = _whoCanView,
          whoCanEdit = _whoCanEdit,
          edited = _edited,
          created = _created,
          editorId = _editorId,
          views = _views,
          parent = _parent,
          parent2 = _parent2,
          source = _source,
          html = _html,
          viewUrl = _viewUrl
        )
      }
    }

  implicit lazy val productEncoder: Encoder[Product] =
    (x: Product) => {
      Json.fromFields(
        List(
          "id"           -> x.id.asJson,
          "owner_id"     -> x.ownerId.asJson,
          "title"        -> x.title.asJson,
          "description"  -> x.description.asJson,
          "price"        -> x.price.asJson,
          "category"     -> x.category.asJson,
          "thumb_photo"  -> x.thumbPhoto.asJson,
          "date"         -> x.date.asJson,
          "availability" -> x.availability.asJson,
          "is_favorite"  -> x.isFavorite.asJson,
          "photos"       -> x.photos.asJson,
          "can_comment"  -> x.canComment.asJson,
          "can_repost"   -> x.canRepost.asJson,
          "likes"        -> x.likes.asJson,
          "url"          -> x.url.asJson,
          "button_title" -> x.buttonTitle.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val productDecoder: Decoder[Product] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _ownerId      <- h.get[Int]("owner_id")
        _title        <- h.get[String]("title")
        _description  <- h.get[String]("description")
        _price        <- h.get[Price]("price")
        _category     <- h.get[Category]("category")
        _thumbPhoto   <- h.get[String]("thumb_photo")
        _date         <- h.get[Int]("date")
        _availability <- h.get[Int]("availability")
        _isFavorite   <- h.get[Boolean]("is_favorite")
        _photos       <- h.get[Vector[Photo]]("photos")
        _canComment   <- h.get[Boolean]("can_comment")
        _canRepost    <- h.get[Boolean]("can_repost")
        _likes        <- h.get[Likes]("likes")
        _url          <- h.get[String]("url")
        _buttonTitle  <- h.get[String]("button_title")
      } yield {
        Product(
          id = _id,
          ownerId = _ownerId,
          title = _title,
          description = _description,
          price = _price,
          category = _category,
          thumbPhoto = _thumbPhoto,
          date = _date,
          availability = _availability,
          isFavorite = _isFavorite,
          photos = _photos,
          canComment = _canComment,
          canRepost = _canRepost,
          likes = _likes,
          url = _url,
          buttonTitle = _buttonTitle
        )
      }
    }

  implicit lazy val priceEncoder: Encoder[Price] =
    (x: Price) => {
      Json.fromFields(
        List(
          "amount"   -> x.amount.asJson,
          "currency" -> x.currency.asJson,
          "text"     -> x.text.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val priceDecoder: Decoder[Price] =
    Decoder.instance { h =>
      for {
        _amount   <- h.get[Int]("amount")
        _currency <- h.get[Currency]("currency")
        _text     <- h.get[String]("text")
      } yield {
        Price(amount = _amount, currency = _currency, text = _text)
      }
    }

  implicit lazy val currencyEncoder: Encoder[Currency] =
    (x: Currency) => {
      Json.fromFields(
        List(
          "id"   -> x.id.asJson,
          "name" -> x.name.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val currencyDecoder: Decoder[Currency] =
    Decoder.instance { h =>
      for {
        _id   <- h.get[Int]("id")
        _name <- h.get[String]("name")
      } yield {
        Currency(id = _id, name = _name)
      }
    }

  implicit lazy val categoryEncoder: Encoder[Category] =
    (x: Category) => {
      Json.fromFields(
        List(
          "id"      -> x.id.asJson,
          "name"    -> x.name.asJson,
          "section" -> x.section.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val categoryDecoder: Decoder[Category] =
    Decoder.instance { h =>
      for {
        _id      <- h.get[Int]("id")
        _name    <- h.get[String]("name")
        _section <- h.get[Section]("section")
      } yield {
        Category(id = _id, name = _name, section = _section)
      }
    }

  implicit lazy val sectionEncoder: Encoder[Section] =
    (x: Section) => {
      Json.fromFields(
        List(
          "id"   -> x.id.asJson,
          "name" -> x.name.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sectionDecoder: Decoder[Section] =
    Decoder.instance { h =>
      for {
        _id   <- h.get[Int]("id")
        _name <- h.get[String]("name")
      } yield {
        Section(id = _id, name = _name)
      }
    }

  implicit lazy val likesEncoder: Encoder[Likes] =
    (x: Likes) => {
      Json.fromFields(
        List(
          "user_likes" -> x.userLikes.asJson,
          "can_like"   -> x.canLike.asJson,
          "count"      -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val likesDecoder: Decoder[Likes] =
    Decoder.instance { h =>
      for {
        _userLikes <- h.get[Boolean]("user_likes")
        _canLike   <- h.get[Boolean]("can_like")
        _count     <- h.get[Int]("count")
      } yield {
        Likes(userLikes = _userLikes, canLike = _canLike, count = _count)
      }
    }

  implicit lazy val productcollectionEncoder: Encoder[ProductCollection] =
    (x: ProductCollection) => {
      Json.fromFields(
        List(
          "id"           -> x.id.asJson,
          "owner_id"     -> x.ownerId.asJson,
          "title"        -> x.title.asJson,
          "photo"        -> x.photo.asJson,
          "count"        -> x.count.asJson,
          "updated_time" -> x.updatedTime.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val productcollectionDecoder: Decoder[ProductCollection] =
    Decoder.instance { h =>
      for {
        _id          <- h.get[Int]("id")
        _ownerId     <- h.get[Int]("owner_id")
        _title       <- h.get[String]("title")
        _photo       <- h.get[Photo]("photo")
        _count       <- h.get[Int]("count")
        _updatedTime <- h.get[Int]("updated_time")
      } yield {
        ProductCollection(id = _id,
                          ownerId = _ownerId,
                          title = _title,
                          photo = _photo,
                          count = _count,
                          updatedTime = _updatedTime)
      }
    }

  implicit lazy val discussionEncoder: Encoder[Discussion] =
    (x: Discussion) => {
      Json.fromFields(
        List(
          "id"            -> x.id.asJson,
          "title"         -> x.title.asJson,
          "created"       -> x.created.asJson,
          "created_by"    -> x.createdBy.asJson,
          "updated"       -> x.updated.asJson,
          "update_by"     -> x.updateBy.asJson,
          "is_closed"     -> x.isClosed.asJson,
          "is_fixed"      -> x.isFixed.asJson,
          "comments"      -> x.comments.asJson,
          "first_comment" -> x.firstComment.asJson,
          "last_comment"  -> x.lastComment.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val discussionDecoder: Decoder[Discussion] =
    Decoder.instance { h =>
      for {
        _id           <- h.get[Int]("id")
        _title        <- h.get[String]("title")
        _created      <- h.get[Int]("created")
        _createdBy    <- h.get[Int]("created_by")
        _updated      <- h.get[Int]("updated")
        _updateBy     <- h.get[Int]("update_by")
        _isClosed     <- h.get[Boolean]("is_closed")
        _isFixed      <- h.get[Boolean]("is_fixed")
        _comments     <- h.get[Int]("comments")
        _firstComment <- h.get[String]("first_comment")
        _lastComment  <- h.get[String]("last_comment")
      } yield {
        Discussion(
          id = _id,
          title = _title,
          created = _created,
          createdBy = _createdBy,
          updated = _updated,
          updateBy = _updateBy,
          isClosed = _isClosed,
          isFixed = _isFixed,
          comments = _comments,
          firstComment = _firstComment,
          lastComment = _lastComment
        )
      }
    }

  implicit lazy val discussioncommentEncoder: Encoder[DiscussionComment] =
    (x: DiscussionComment) => {
      Json.fromFields(
        List(
          "id"          -> x.id.asJson,
          "from_id"     -> x.fromId.asJson,
          "date"        -> x.date.asJson,
          "text"        -> x.text.asJson,
          "attachments" -> x.attachments.asJson,
          "likes"       -> x.likes.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val discussioncommentDecoder: Decoder[DiscussionComment] =
    Decoder.instance { h =>
      for {
        _id          <- h.get[Int]("id")
        _fromId      <- h.get[Int]("from_id")
        _date        <- h.get[Int]("date")
        _text        <- h.get[String]("text")
        _attachments <- h.get[Vector[Attachment]]("attachments")
        _likes       <- h.get[Likes]("likes")
      } yield {
        DiscussionComment(id = _id,
                          fromId = _fromId,
                          date = _date,
                          text = _text,
                          attachments = _attachments,
                          likes = _likes)
      }
    }

  implicit lazy val applicationEncoder: Encoder[Application] =
    (x: Application) => {
      Json.fromFields(
        List(
          "id"                       -> x.id.asJson,
          "title"                    -> x.title.asJson,
          "icon278"                  -> x.icon278.asJson,
          "icon139"                  -> x.icon139.asJson,
          "icon150"                  -> x.icon150.asJson,
          "icon75"                   -> x.icon75.asJson,
          "banner560"                -> x.banner560.asJson,
          "banner1120"               -> x.banner1120.asJson,
          "type"                     -> x.`type`.asJson,
          "section"                  -> x.section.asJson,
          "author_url"               -> x.authorUrl.asJson,
          "author_id"                -> x.authorId.asJson,
          "author_group"             -> x.authorGroup.asJson,
          "members_count"            -> x.membersCount.asJson,
          "published_date"           -> x.publishedDate.asJson,
          "catalog_position"         -> x.catalogPosition.asJson,
          "international"            -> x.international.asJson,
          "leaderboard_type"         -> x.leaderboardType.asJson,
          "genre_id"                 -> x.genreId.asJson,
          "genre"                    -> x.genre.asJson,
          "platform_id"              -> x.platformId.asJson,
          "is_in_catalog"            -> x.isInCatalog.asJson,
          "friends"                  -> x.friends.asJson,
          "installed"                -> x.installed.asJson,
          "is_html5_app"             -> x.isHtml5App.asJson,
          "screen_orientation"       -> x.screenOrientation.asJson,
          "mobile_controls_type"     -> x.mobileControlsType.asJson,
          "mobile_view_support_type" -> x.mobileViewSupportType.asJson,
          "description"              -> x.description.asJson,
          "screen_name"              -> x.screenName.asJson,
          "icon16"                   -> x.icon16.asJson,
          "screenshots"              -> x.screenshots.asJson,
          "push_enabled"             -> x.pushEnabled.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val applicationDecoder: Decoder[Application] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[Int]("id")
        _title                 <- h.get[String]("title")
        _icon278               <- h.get[String]("icon278")
        _icon139               <- h.get[String]("icon139")
        _icon150               <- h.get[String]("icon150")
        _icon75                <- h.get[String]("icon75")
        _banner560             <- h.get[String]("banner560")
        _banner1120            <- h.get[String]("banner1120")
        _type                  <- h.get[String]("type")
        _section               <- h.get[String]("section")
        _authorUrl             <- h.get[String]("author_url")
        _authorId              <- h.get[Int]("author_id")
        _authorGroup           <- h.get[Int]("author_group")
        _membersCount          <- h.get[Int]("members_count")
        _publishedDate         <- h.get[Int]("published_date")
        _catalogPosition       <- h.get[Int]("catalog_position")
        _international         <- h.get[Boolean]("international")
        _leaderboardType       <- h.get[Integer]("leaderboard_type")
        _genreId               <- h.get[Int]("genre_id")
        _genre                 <- h.get[String]("genre")
        _platformId            <- h.get[String]("platform_id")
        _isInCatalog           <- h.get[Boolean]("is_in_catalog")
        _friends               <- h.get[Vector[Int]]("friends")
        _installed             <- h.get[Boolean]("installed")
        _isHtml5App            <- h.get[Option[Boolean]]("is_html5_app")
        _screenOrientation     <- h.get[Int]("screen_orientation")
        _mobileControlsType    <- h.get[Integer]("mobile_controls_type")
        _mobileViewSupportType <- h.get[Option[Boolean]]("mobile_view_support_type")
        _description           <- h.get[Option[String]]("description")
        _screenName            <- h.get[Option[String]]("screen_name")
        _icon16                <- h.get[Option[String]]("icon16")
        _screenshots           <- h.get[Vector[Photo]]("screenshots")
        _pushEnabled           <- h.get[Boolean]("push_enabled")
      } yield {
        Application(
          id = _id,
          title = _title,
          icon278 = _icon278,
          icon139 = _icon139,
          icon150 = _icon150,
          icon75 = _icon75,
          banner560 = _banner560,
          banner1120 = _banner1120,
          `type` = _type,
          section = _section,
          authorUrl = _authorUrl,
          authorId = _authorId,
          authorGroup = _authorGroup,
          membersCount = _membersCount,
          publishedDate = _publishedDate,
          catalogPosition = _catalogPosition,
          international = _international,
          leaderboardType = _leaderboardType,
          genreId = _genreId,
          genre = _genre,
          platformId = _platformId,
          isInCatalog = _isInCatalog,
          friends = _friends,
          installed = _installed,
          isHtml5App = _isHtml5App,
          screenOrientation = _screenOrientation,
          mobileControlsType = _mobileControlsType,
          mobileViewSupportType = _mobileViewSupportType,
          description = _description,
          screenName = _screenName,
          icon16 = _icon16,
          screenshots = _screenshots,
          pushEnabled = _pushEnabled
        )
      }
    }

  implicit lazy val stickerEncoder: Encoder[Sticker] =
    (x: Sticker) => {
      Json.fromFields(
        List(
          "product_id"             -> x.productId.asJson,
          "sticker_id"             -> x.stickerId.asJson,
          "images"                 -> x.images.asJson,
          "images_with_background" -> x.imagesWithBackground.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickerDecoder: Decoder[Sticker] =
    Decoder.instance { h =>
      for {
        _productId            <- h.get[Int]("product_id")
        _stickerId            <- h.get[Int]("sticker_id")
        _images               <- h.get[Vector[StickerImage]]("images")
        _imagesWithBackground <- h.get[Vector[StickerImage]]("images_with_background")
      } yield {
        Sticker(productId = _productId,
                stickerId = _stickerId,
                images = _images,
                imagesWithBackground = _imagesWithBackground)
      }
    }

  implicit lazy val stickerimageEncoder: Encoder[StickerImage] =
    (x: StickerImage) => {
      Json.fromFields(
        List(
          "url"    -> x.url.asJson,
          "width"  -> x.width.asJson,
          "height" -> x.height.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickerimageDecoder: Decoder[StickerImage] =
    Decoder.instance { h =>
      for {
        _url    <- h.get[String]("url")
        _width  <- h.get[Int]("width")
        _height <- h.get[Int]("height")
      } yield {
        StickerImage(url = _url, width = _width, height = _height)
      }
    }

  implicit lazy val giftEncoder: Encoder[Gift] =
    (x: Gift) => {
      Json.fromFields(
        List(
          "id"       -> x.id.asJson,
          "thumb256" -> x.thumb256.asJson,
          "thumb96"  -> x.thumb96.asJson,
          "thumb48"  -> x.thumb48.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giftDecoder: Decoder[Gift] =
    Decoder.instance { h =>
      for {
        _id       <- h.get[Int]("id")
        _thumb256 <- h.get[String]("thumb256")
        _thumb96  <- h.get[String]("thumb96")
        _thumb48  <- h.get[String]("thumb48")
      } yield {
        Gift(id = _id, thumb256 = _thumb256, thumb96 = _thumb96, thumb48 = _thumb48)
      }
    }

  implicit lazy val pollEncoder: Encoder[Poll] =
    (x: Poll) => {
      Json.fromFields(
        List(
          "id"         -> x.id.asJson,
          "owner_id"   -> x.ownerId.asJson,
          "created"    -> x.created.asJson,
          "question"   -> x.question.asJson,
          "votes"      -> x.votes.asJson,
          "answers"    -> x.answers.asJson,
          "anonymous"  -> x.anonymous.asJson,
          "multiple"   -> x.multiple.asJson,
          "answer_ids" -> x.answerIds.asJson,
          "end_date"   -> x.endDate.asJson,
          "closed"     -> x.closed.asJson,
          "is_board"   -> x.isBoard.asJson,
          "can_edit"   -> x.canEdit.asJson,
          "can_vote"   -> x.canVote.asJson,
          "can_report" -> x.canReport.asJson,
          "can_share"  -> x.canShare.asJson,
          "author_id"  -> x.authorId.asJson,
          "photo"      -> x.photo.asJson,
          "background" -> x.background.asJson,
          "friends"    -> x.friends.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pollDecoder: Decoder[Poll] =
    Decoder.instance { h =>
      for {
        _id         <- h.get[Int]("id")
        _ownerId    <- h.get[Int]("owner_id")
        _created    <- h.get[Int]("created")
        _question   <- h.get[String]("question")
        _votes      <- h.get[Int]("votes")
        _answers    <- h.get[Vector[Answer]]("answers")
        _anonymous  <- h.get[Boolean]("anonymous")
        _multiple   <- h.get[Boolean]("multiple")
        _answerIds  <- h.get[Vector[Int]]("answer_ids")
        _endDate    <- h.get[Integer]("end_date")
        _closed     <- h.get[Boolean]("closed")
        _isBoard    <- h.get[Boolean]("is_board")
        _canEdit    <- h.get[Boolean]("can_edit")
        _canVote    <- h.get[Boolean]("can_vote")
        _canReport  <- h.get[Boolean]("can_report")
        _canShare   <- h.get[Boolean]("can_share")
        _authorId   <- h.get[Integer]("author_id")
        _photo      <- h.get[Photo]("photo")
        _background <- h.get[Background]("background")
        _friends    <- h.get[Vector[Int]]("friends")
      } yield {
        Poll(
          id = _id,
          ownerId = _ownerId,
          created = _created,
          question = _question,
          votes = _votes,
          answers = _answers,
          anonymous = _anonymous,
          multiple = _multiple,
          answerIds = _answerIds,
          endDate = _endDate,
          closed = _closed,
          isBoard = _isBoard,
          canEdit = _canEdit,
          canVote = _canVote,
          canReport = _canReport,
          canShare = _canShare,
          authorId = _authorId,
          photo = _photo,
          background = _background,
          friends = _friends
        )
      }
    }

  implicit lazy val answerEncoder: Encoder[Answer] =
    (x: Answer) => {
      Json.fromFields(
        List(
          "id"    -> x.id.asJson,
          "text"  -> x.text.asJson,
          "votes" -> x.votes.asJson,
          "rate"  -> x.rate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answerDecoder: Decoder[Answer] =
    Decoder.instance { h =>
      for {
        _id    <- h.get[Integer]("id")
        _text  <- h.get[String]("text")
        _votes <- h.get[Integer]("votes")
        _rate  <- h.get[Int]("rate")
      } yield {
        Answer(id = _id, text = _text, votes = _votes, rate = _rate)
      }
    }

  implicit lazy val backgroundEncoder: Encoder[Background] =
    (x: Background) => {
      Json.fromFields(
        List(
          "id"     -> x.id.asJson,
          "type"   -> x.`type`.asJson,
          "angle"  -> x.angle.asJson,
          "color"  -> x.color.asJson,
          "width"  -> x.width.asJson,
          "height" -> x.height.asJson,
          "images" -> x.images.asJson,
          "points" -> x.points.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundDecoder: Decoder[Background] =
    Decoder.instance { h =>
      for {
        _id     <- h.get[Integer]("id")
        _type   <- h.get[String]("type")
        _angle  <- h.get[Integer]("angle")
        _color  <- h.get[String]("color")
        _width  <- h.get[Integer]("width")
        _height <- h.get[Integer]("height")
        _images <- h.get[Vector[Photo]]("images")
        _points <- h.get[Vector[Point]]("points")
      } yield {
        Background(id = _id,
                   `type` = _type,
                   angle = _angle,
                   color = _color,
                   width = _width,
                   height = _height,
                   images = _images,
                   points = _points)
      }
    }

  implicit lazy val pointEncoder: Encoder[Point] =
    (x: Point) => {
      Json.fromFields(
        List(
          "position" -> x.position.asJson,
          "color"    -> x.color.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pointDecoder: Decoder[Point] =
    Decoder.instance { h =>
      for {
        _position <- h.get[Integer]("position")
        _color    <- h.get[String]("color")
      } yield {
        Point(position = _position, color = _color)
      }
    }

  implicit lazy val periodfromEncoder: Encoder[PeriodFrom] =
    (x: PeriodFrom) => {
      Json.fromFields(
        List(
          "period_from" -> x.periodFrom.asJson,
          "period_to"   -> x.periodTo.asJson,
          "visitors"    -> x.visitors.asJson,
          "reach"       -> x.reach.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val periodfromDecoder: Decoder[PeriodFrom] =
    Decoder.instance { h =>
      for {
        _periodFrom <- h.get[String]("period_from")
        _periodTo   <- h.get[String]("period_to")
        _visitors   <- h.get[Visitors]("visitors")
        _reach      <- h.get[Reach]("reach")
      } yield {
        PeriodFrom(periodFrom = _periodFrom,
                   periodTo = _periodTo,
                   visitors = _visitors,
                   reach = _reach)
      }
    }

  implicit lazy val visitorsEncoder: Encoder[Visitors] =
    (x: Visitors) => {
      Json.fromFields(
        List(
          "views"    -> x.views.asJson,
          "visitors" -> x.visitors.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val visitorsDecoder: Decoder[Visitors] =
    Decoder.instance { h =>
      for {
        _views    <- h.get[Int]("views")
        _visitors <- h.get[Int]("visitors")
      } yield {
        Visitors(views = _views, visitors = _visitors)
      }
    }

  implicit lazy val reachEncoder: Encoder[Reach] =
    (x: Reach) => {
      Json.fromFields(
        List(
          "reach"             -> x.reach.asJson,
          "reach_subscribers" -> x.reachSubscribers.asJson,
          "mobile_reach"      -> x.mobileReach.asJson,
          "sex"               -> x.sex.asJson,
          "age"               -> x.age.asJson,
          "sex_age"           -> x.sexAge.asJson,
          "cities"            -> x.cities.asJson,
          "countries"         -> x.countries.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val reachDecoder: Decoder[Reach] =
    Decoder.instance { h =>
      for {
        _reach            <- h.get[Integer]("reach")
        _reachSubscribers <- h.get[Int]("reach_subscribers")
        _mobileReach      <- h.get[Int]("mobile_reach")
        _sex              <- h.get[Vector[SexStat]]("sex")
        _age              <- h.get[Vector[AgeStat]]("age")
        _sexAge           <- h.get[Vector[SexAgeStat]]("sex_age")
        _cities           <- h.get[Vector[CitiesStat]]("cities")
        _countries        <- h.get[Vector[CountriesStat]]("countries")
      } yield {
        Reach(reach = _reach,
              reachSubscribers = _reachSubscribers,
              mobileReach = _mobileReach,
              sex = _sex,
              age = _age,
              sexAge = _sexAge,
              cities = _cities,
              countries = _countries)
      }
    }

  implicit lazy val sexstatEncoder: Encoder[SexStat] =
    (x: SexStat) => {
      Json.fromFields(
        List(
          "value" -> x.value.asJson,
          "count" -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sexstatDecoder: Decoder[SexStat] =
    Decoder.instance { h =>
      for {
        _value <- h.get[String]("value")
        _count <- h.get[Int]("count")
      } yield {
        SexStat(value = _value, count = _count)
      }
    }

  implicit lazy val agestatEncoder: Encoder[AgeStat] =
    (x: AgeStat) => {
      Json.fromFields(
        List(
          "value" -> x.value.asJson,
          "count" -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val agestatDecoder: Decoder[AgeStat] =
    Decoder.instance { h =>
      for {
        _value <- h.get[String]("value")
        _count <- h.get[Int]("count")
      } yield {
        AgeStat(value = _value, count = _count)
      }
    }

  implicit lazy val sexagestatEncoder: Encoder[SexAgeStat] =
    (x: SexAgeStat) => {
      Json.fromFields(
        List(
          "value" -> x.value.asJson,
          "count" -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sexagestatDecoder: Decoder[SexAgeStat] =
    Decoder.instance { h =>
      for {
        _value <- h.get[String]("value")
        _count <- h.get[Integer]("count")
      } yield {
        SexAgeStat(value = _value, count = _count)
      }
    }

  implicit lazy val citiesstatEncoder: Encoder[CitiesStat] =
    (x: CitiesStat) => {
      Json.fromFields(
        List(
          "name"    -> x.name.asJson,
          "city_id" -> x.cityId.asJson,
          "count"   -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val citiesstatDecoder: Decoder[CitiesStat] =
    Decoder.instance { h =>
      for {
        _name   <- h.get[String]("name")
        _cityId <- h.get[String]("city_id")
        _count  <- h.get[Int]("count")
      } yield {
        CitiesStat(name = _name, cityId = _cityId, count = _count)
      }
    }

  implicit lazy val countriesstatEncoder: Encoder[CountriesStat] =
    (x: CountriesStat) => {
      Json.fromFields(
        List(
          "name"       -> x.name.asJson,
          "code"       -> x.code.asJson,
          "country_id" -> x.countryId.asJson,
          "count"      -> x.count.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val countriesstatDecoder: Decoder[CountriesStat] =
    Decoder.instance { h =>
      for {
        _name      <- h.get[String]("name")
        _code      <- h.get[String]("code")
        _countryId <- h.get[Int]("country_id")
        _count     <- h.get[Int]("count")
      } yield {
        CountriesStat(name = _name, code = _code, countryId = _countryId, count = _count)
      }
    }

  implicit lazy val addressEncoder: Encoder[Address] =
    (x: Address) => {
      Json.fromFields(
        List(
          "id"                 -> x.id.asJson,
          "country_id"         -> x.countryId.asJson,
          "city_id"            -> x.cityId.asJson,
          "title"              -> x.title.asJson,
          "address"            -> x.address.asJson,
          "additional_address" -> x.additionalAddress.asJson,
          "latitude"           -> x.latitude.asJson,
          "longitude"          -> x.longitude.asJson,
          "metro_station_id"   -> x.metroStationId.asJson,
          "work_info_status"   -> x.workInfoStatus.asJson,
          "timetable"          -> x.timetable.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val addressDecoder: Decoder[Address] =
    Decoder.instance { h =>
      for {
        _id                <- h.get[Integer]("id")
        _countryId         <- h.get[Int]("country_id")
        _cityId            <- h.get[Int]("city_id")
        _title             <- h.get[String]("title")
        _address           <- h.get[String]("address")
        _additionalAddress <- h.get[String]("additional_address")
        _latitude          <- h.get[Double]("latitude")
        _longitude         <- h.get[Double]("longitude")
        _metroStationId    <- h.get[Int]("metro_station_id")
        _workInfoStatus    <- h.get[String]("work_info_status")
        _timetable         <- h.get[String]("timetable")
      } yield {
        Address(
          id = _id,
          countryId = _countryId,
          cityId = _cityId,
          title = _title,
          address = _address,
          additionalAddress = _additionalAddress,
          latitude = _latitude,
          longitude = _longitude,
          metroStationId = _metroStationId,
          workInfoStatus = _workInfoStatus,
          timetable = _timetable
        )
      }
    }

  implicit lazy val documentEncoder: Encoder[Document] =
    (x: Document) => {
      Json.fromFields(
        List(
          "id"       -> x.id.asJson,
          "owner_id" -> x.ownerId.asJson,
          "title"    -> x.title.asJson,
          "size"     -> x.size.asJson,
          "ext"      -> x.ext.asJson,
          "url"      -> x.url.asJson,
          "date"     -> x.date.asJson,
          "type"     -> x.`type`.asJson,
          "preview"  -> x.preview.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val documentDecoder: Decoder[Document] =
    Decoder.instance { h =>
      for {
        _id      <- h.get[Integer]("id")
        _ownerId <- h.get[Integer]("owner_id")
        _title   <- h.get[String]("title")
        _size    <- h.get[Integer]("size")
        _ext     <- h.get[String]("ext")
        _url     <- h.get[String]("url")
        _date    <- h.get[Integer]("date")
        _type    <- h.get[Int]("type")
        _preview <- h.get[Preview]("preview")
      } yield {
        Document(id = _id,
                 ownerId = _ownerId,
                 title = _title,
                 size = _size,
                 ext = _ext,
                 url = _url,
                 date = _date,
                 `type` = _type,
                 preview = _preview)
      }
    }

  implicit lazy val previewEncoder: Encoder[Preview] =
    (x: Preview) => {
      Json.fromFields(
        List(
          "photo"         -> x.photo.asJson,
          "graffiti"      -> x.graffiti.asJson,
          "audio_message" -> x.audioMessage.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val previewDecoder: Decoder[Preview] =
    Decoder.instance { h =>
      for {
        _photo        <- h.get[PreviewPhoto]("photo")
        _graffiti     <- h.get[Graffiti]("graffiti")
        _audioMessage <- h.get[AudioMessage]("audio_message")
      } yield {
        Preview(photo = _photo, graffiti = _graffiti, audioMessage = _audioMessage)
      }
    }

  implicit lazy val previewphotoEncoder: Encoder[PreviewPhoto] =
    (x: PreviewPhoto) => {
      Json.fromFields(
        List(
          "sizes" -> x.sizes.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val previewphotoDecoder: Decoder[PreviewPhoto] =
    Decoder.instance { h =>
      for {
        _sizes <- h.get[Vector[SizedPhoto]]("sizes")
      } yield {
        PreviewPhoto(sizes = _sizes)
      }
    }

  implicit lazy val graffitiEncoder: Encoder[Graffiti] =
    (x: Graffiti) => {
      Json.fromFields(
        List(
          "src"    -> x.src.asJson,
          "width"  -> x.width.asJson,
          "height" -> x.height.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val graffitiDecoder: Decoder[Graffiti] =
    Decoder.instance { h =>
      for {
        _src    <- h.get[String]("src")
        _width  <- h.get[Integer]("width")
        _height <- h.get[Integer]("height")
      } yield {
        Graffiti(src = _src, width = _width, height = _height)
      }
    }

  implicit lazy val audiomessageEncoder: Encoder[AudioMessage] =
    (x: AudioMessage) => {
      Json.fromFields(
        List(
          "duration" -> x.duration.asJson,
          "waveform" -> x.waveform.asJson,
          "link_ogg" -> x.linkOgg.asJson,
          "link_mp3" -> x.linkMp3.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val audiomessageDecoder: Decoder[AudioMessage] =
    Decoder.instance { h =>
      for {
        _duration <- h.get[Integer]("duration")
        _waveform <- h.get[Vector[Int]]("waveform")
        _linkOgg  <- h.get[String]("link_ogg")
        _linkMp3  <- h.get[String]("link_mp3")
      } yield {
        AudioMessage(duration = _duration,
                     waveform = _waveform,
                     linkOgg = _linkOgg,
                     linkMp3 = _linkMp3)
      }
    }

  implicit lazy val historyEncoder: Encoder[History] =
    (x: History) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "owner_id"              -> x.ownerId.asJson,
          "date"                  -> x.date.asJson,
          "expires_at"            -> x.expiresAt.asJson,
          "is_expired"            -> x.isExpired.asJson,
          "is_deleted"            -> x.isDeleted.asJson,
          "can_see"               -> x.canSee.asJson,
          "seen"                  -> x.seen.asJson,
          "type"                  -> x.`type`.asJson,
          "photo"                 -> x.photo.asJson,
          "video"                 -> x.video.asJson,
          "link"                  -> x.link.asJson,
          "parent_story_owner_id" -> x.parentStoryOwnerId.asJson,
          "parent_story_id"       -> x.parentStoryId.asJson,
          "parent_story"          -> x.parentStory.asJson,
          "replies"               -> x.replies.asJson,
          "can_reply"             -> x.canReply.asJson,
          "can_share"             -> x.canShare.asJson,
          "can_comment"           -> x.canComment.asJson,
          "views"                 -> x.views.asJson,
          "access_key"            -> x.accessKey.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val historyDecoder: Decoder[History] =
    Decoder.instance { h =>
      for {
        _id                 <- h.get[Integer]("id")
        _ownerId            <- h.get[Integer]("owner_id")
        _date               <- h.get[Integer]("date")
        _expiresAt          <- h.get[Integer]("expires_at")
        _isExpired          <- h.get[Boolean]("is_expired")
        _isDeleted          <- h.get[Boolean]("is_deleted")
        _canSee             <- h.get[Boolean]("can_see")
        _seen               <- h.get[Option[Integer]]("seen")
        _type               <- h.get[String]("type")
        _photo              <- h.get[Option[Photo]]("photo")
        _video              <- h.get[Option[HistoryVideo]]("video")
        _link               <- h.get[Link]("link")
        _parentStoryOwnerId <- h.get[Integer]("parent_story_owner_id")
        _parentStoryId      <- h.get[Integer]("parent_story_id")
        _parentStory        <- h.get[History]("parent_story")
        _replies            <- h.get[HistoryReplies]("replies")
        _canReply           <- h.get[Boolean]("can_reply")
        _canShare           <- h.get[Boolean]("can_share")
        _canComment         <- h.get[Boolean]("can_comment")
        _views              <- h.get[Integer]("views")
        _accessKey          <- h.get[String]("access_key")
      } yield {
        History(
          id = _id,
          ownerId = _ownerId,
          date = _date,
          expiresAt = _expiresAt,
          isExpired = _isExpired,
          isDeleted = _isDeleted,
          canSee = _canSee,
          seen = _seen,
          `type` = _type,
          photo = _photo,
          video = _video,
          link = _link,
          parentStoryOwnerId = _parentStoryOwnerId,
          parentStoryId = _parentStoryId,
          parentStory = _parentStory,
          replies = _replies,
          canReply = _canReply,
          canShare = _canShare,
          canComment = _canComment,
          views = _views,
          accessKey = _accessKey
        )
      }
    }

  implicit lazy val historyvideoEncoder: Encoder[HistoryVideo] =
    (x: HistoryVideo) => {
      Json.fromFields(
        List(
          "id"              -> x.id.asJson,
          "owner_id"        -> x.ownerId.asJson,
          "title"           -> x.title.asJson,
          "description"     -> x.description.asJson,
          "duration"        -> x.duration.asJson,
          "photo130"        -> x.photo130.asJson,
          "photo320"        -> x.photo320.asJson,
          "photo640"        -> x.photo640.asJson,
          "photo800"        -> x.photo800.asJson,
          "photo1280"       -> x.photo1280.asJson,
          "first_frame130"  -> x.firstFrame130.asJson,
          "first_frame320"  -> x.firstFrame320.asJson,
          "first_frame640"  -> x.firstFrame640.asJson,
          "first_frame800"  -> x.firstFrame800.asJson,
          "first_frame1280" -> x.firstFrame1280.asJson,
          "date"            -> x.date.asJson,
          "adding_date"     -> x.addingDate.asJson,
          "views"           -> x.views.asJson,
          "comments"        -> x.comments.asJson,
          "player"          -> x.player.asJson,
          "platform"        -> x.platform.asJson,
          "can_edit"        -> x.canEdit.asJson,
          "can_add"         -> x.canAdd.asJson,
          "is_private"      -> x.isPrivate.asJson,
          "access_key"      -> x.accessKey.asJson,
          "processing"      -> x.processing.asJson,
          "live"            -> x.live.asJson,
          "upcoming"        -> x.upcoming.asJson,
          "is_favorite"     -> x.isFavorite.asJson,
          "first_frame800"  -> x.firstFrame800.asJson,
          "first_frame320"  -> x.firstFrame320.asJson,
          "first_frame160"  -> x.firstFrame160.asJson,
          "first_frame130"  -> x.firstFrame130.asJson,
          "is_private"      -> x.isPrivate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val historyvideoDecoder: Decoder[HistoryVideo] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[Int]("id")
        _ownerId        <- h.get[Int]("owner_id")
        _title          <- h.get[String]("title")
        _description    <- h.get[String]("description")
        _duration       <- h.get[Int]("duration")
        _photo130       <- h.get[String]("photo130")
        _photo320       <- h.get[String]("photo320")
        _photo640       <- h.get[String]("photo640")
        _photo800       <- h.get[String]("photo800")
        _photo1280      <- h.get[String]("photo1280")
        _firstFrame130  <- h.get[String]("first_frame130")
        _firstFrame320  <- h.get[String]("first_frame320")
        _firstFrame640  <- h.get[String]("first_frame640")
        _firstFrame800  <- h.get[String]("first_frame800")
        _firstFrame1280 <- h.get[String]("first_frame1280")
        _date           <- h.get[Long]("date")
        _addingDate     <- h.get[Long]("adding_date")
        _views          <- h.get[Int]("views")
        _comments       <- h.get[Int]("comments")
        _player         <- h.get[String]("player")
        _platform       <- h.get[String]("platform")
        _canEdit        <- h.get[Option[Int]]("can_edit")
        _canAdd         <- h.get[Int]("can_add")
        _isPrivate      <- h.get[Option[Int]]("is_private")
        _accessKey      <- h.get[Int]("access_key")
        _processing     <- h.get[Option[Int]]("processing")
        _live           <- h.get[Option[Int]]("live")
        _upcoming       <- h.get[Option[Int]]("upcoming")
        _isFavorite     <- h.get[Boolean]("is_favorite")
        _firstFrame800  <- h.get[String]("first_frame800")
        _firstFrame320  <- h.get[String]("first_frame320")
        _firstFrame160  <- h.get[String]("first_frame160")
        _firstFrame130  <- h.get[String]("first_frame130")
        _isPrivate      <- h.get[Boolean]("is_private")
      } yield {
        HistoryVideo(
          id = _id,
          ownerId = _ownerId,
          title = _title,
          description = _description,
          duration = _duration,
          photo130 = _photo130,
          photo320 = _photo320,
          photo640 = _photo640,
          photo800 = _photo800,
          photo1280 = _photo1280,
          firstFrame130 = _firstFrame130,
          firstFrame320 = _firstFrame320,
          firstFrame640 = _firstFrame640,
          firstFrame800 = _firstFrame800,
          firstFrame1280 = _firstFrame1280,
          date = _date,
          addingDate = _addingDate,
          views = _views,
          comments = _comments,
          player = _player,
          platform = _platform,
          canEdit = _canEdit,
          canAdd = _canAdd,
          isPrivate = _isPrivate,
          accessKey = _accessKey,
          processing = _processing,
          live = _live,
          upcoming = _upcoming,
          isFavorite = _isFavorite,
          firstFrame800 = _firstFrame800,
          firstFrame320 = _firstFrame320,
          firstFrame160 = _firstFrame160,
          firstFrame130 = _firstFrame130,
          isPrivate = _isPrivate
        )
      }
    }

  implicit lazy val historyrepliesEncoder: Encoder[HistoryReplies] =
    (x: HistoryReplies) => {
      Json.fromFields(
        List(
          "count" -> x.count.asJson,
          "neww"  -> x.neww.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val historyrepliesDecoder: Decoder[HistoryReplies] =
    Decoder.instance { h =>
      for {
        _count <- h.get[Integer]("count")
        _neww  <- h.get[Integer]("neww")
      } yield {
        HistoryReplies(count = _count, neww = _neww)
      }
    }

  implicit lazy val communityEncoder: Encoder[Community] =
    (x: Community) => {
      Json.fromFields(
        List(
          "id"                  -> x.id.asJson,
          "name"                -> x.name.asJson,
          "screen_name"         -> x.screenName.asJson,
          "is_closed"           -> x.isClosed.asJson,
          "deactivated"         -> x.deactivated.asJson,
          "is_admin"            -> x.isAdmin.asJson,
          "admin_level"         -> x.adminLevel.asJson,
          "is_member"           -> x.isMember.asJson,
          "is_advertiser"       -> x.isAdvertiser.asJson,
          "invited_by"          -> x.invitedBy.asJson,
          "type"                -> x.`type`.asJson,
          "photo50"             -> x.photo50.asJson,
          "photo100"            -> x.photo100.asJson,
          "photo200"            -> x.photo200.asJson,
          "activity"            -> x.activity.asJson,
          "age_limits"          -> x.ageLimits.asJson,
          "ban_info"            -> x.banInfo.asJson,
          "can_create_topic"    -> x.canCreateTopic.asJson,
          "can_message"         -> x.canMessage.asJson,
          "can_post"            -> x.canPost.asJson,
          "can_see_all_posts"   -> x.canSeeAllPosts.asJson,
          "can_upload_doc"      -> x.canUploadDoc.asJson,
          "can_upload_video"    -> x.canUploadVideo.asJson,
          "city"                -> x.city.asJson,
          "contacts"            -> x.contacts.asJson,
          "country"             -> x.country.asJson,
          "cover"               -> x.cover.asJson,
          "crop_photo"          -> x.cropPhoto.asJson,
          "description"         -> x.description.asJson,
          "fixed_post"          -> x.fixedPost.asJson,
          "has_photo"           -> x.hasPhoto.asJson,
          "is_favorite"         -> x.isFavorite.asJson,
          "is_hidden_from_feed" -> x.isHiddenFromFeed.asJson,
          "is_messages_blocked" -> x.isMessagesBlocked.asJson,
          "links"               -> x.links.asJson,
          "main_album_id"       -> x.mainAlbumId.asJson,
          "main_section"        -> x.mainSection.asJson,
          "market"              -> x.market.asJson,
          "member_status"       -> x.memberStatus.asJson,
          "members_count"       -> x.membersCount.asJson,
          "place"               -> x.place.asJson,
          "public_date_label"   -> x.publicDateLabel.asJson,
          "site"                -> x.site.asJson,
          "start_date"          -> x.startDate.asJson,
          "finish_date"         -> x.finishDate.asJson,
          "status"              -> x.status.asJson,
          "trending"            -> x.trending.asJson,
          "verified"            -> x.verified.asJson,
          "wall"                -> x.wall.asJson,
          "wiki_page"           -> x.wikiPage.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val communityDecoder: Decoder[Community] =
    Decoder.instance { h =>
      for {
        _id                <- h.get[Integer]("id")
        _name              <- h.get[String]("name")
        _screenName        <- h.get[String]("screen_name")
        _isClosed          <- h.get[Integer]("is_closed")
        _deactivated       <- h.get[Option[String]]("deactivated")
        _isAdmin           <- h.get[Boolean]("is_admin")
        _adminLevel        <- h.get[Integer]("admin_level")
        _isMember          <- h.get[Boolean]("is_member")
        _isAdvertiser      <- h.get[Boolean]("is_advertiser")
        _invitedBy         <- h.get[Integer]("invited_by")
        _type              <- h.get[String]("type")
        _photo50           <- h.get[String]("photo50")
        _photo100          <- h.get[String]("photo100")
        _photo200          <- h.get[String]("photo200")
        _activity          <- h.get[Option[String]]("activity")
        _ageLimits         <- h.get[Int]("age_limits")
        _banInfo           <- h.get[Option[BanInfo]]("ban_info")
        _canCreateTopic    <- h.get[Option[Boolean]]("can_create_topic")
        _canMessage        <- h.get[Option[Boolean]]("can_message")
        _canPost           <- h.get[Option[Boolean]]("can_post")
        _canSeeAllPosts    <- h.get[Option[Boolean]]("can_see_all_posts")
        _canUploadDoc      <- h.get[Option[Boolean]]("can_upload_doc")
        _canUploadVideo    <- h.get[Option[Boolean]]("can_upload_video")
        _city              <- h.get[Option[City]]("city")
        _contacts          <- h.get[Option[CommunityContacts]]("contacts")
        _country           <- h.get[Option[Country]]("country")
        _cover             <- h.get[Option[Cover]]("cover")
        _cropPhoto         <- h.get[Option[CropPhoto]]("crop_photo")
        _description       <- h.get[Option[String]]("description")
        _fixedPost         <- h.get[Option[Int]]("fixed_post")
        _hasPhoto          <- h.get[Option[Boolean]]("has_photo")
        _isFavorite        <- h.get[Option[Boolean]]("is_favorite")
        _isHiddenFromFeed  <- h.get[Option[Boolean]]("is_hidden_from_feed")
        _isMessagesBlocked <- h.get[Option[Boolean]]("is_messages_blocked")
        _links             <- h.get[Vector[CommunityLink]]("links")
        _mainAlbumId       <- h.get[Option[Int]]("main_album_id")
        _mainSection       <- h.get[Option[Int]]("main_section")
        _market            <- h.get[Option[Market]]("market")
        _memberStatus      <- h.get[Option[Int]]("member_status")
        _membersCount      <- h.get[Option[Int]]("members_count")
        _place             <- h.get[Option[Place]]("place")
        _publicDateLabel   <- h.get[Option[String]]("public_date_label")
        _site              <- h.get[Option[String]]("site")
        _startDate         <- h.get[Option[Int]]("start_date")
        _finishDate        <- h.get[Option[Int]]("finish_date")
        _status            <- h.get[Option[String]]("status")
        _trending          <- h.get[Option[Boolean]]("trending")
        _verified          <- h.get[Option[Boolean]]("verified")
        _wall              <- h.get[Option[Int]]("wall")
        _wikiPage          <- h.get[Option[String]]("wiki_page")
      } yield {
        Community(
          id = _id,
          name = _name,
          screenName = _screenName,
          isClosed = _isClosed,
          deactivated = _deactivated,
          isAdmin = _isAdmin,
          adminLevel = _adminLevel,
          isMember = _isMember,
          isAdvertiser = _isAdvertiser,
          invitedBy = _invitedBy,
          `type` = _type,
          photo50 = _photo50,
          photo100 = _photo100,
          photo200 = _photo200,
          activity = _activity,
          ageLimits = _ageLimits,
          banInfo = _banInfo,
          canCreateTopic = _canCreateTopic,
          canMessage = _canMessage,
          canPost = _canPost,
          canSeeAllPosts = _canSeeAllPosts,
          canUploadDoc = _canUploadDoc,
          canUploadVideo = _canUploadVideo,
          city = _city,
          contacts = _contacts,
          country = _country,
          cover = _cover,
          cropPhoto = _cropPhoto,
          description = _description,
          fixedPost = _fixedPost,
          hasPhoto = _hasPhoto,
          isFavorite = _isFavorite,
          isHiddenFromFeed = _isHiddenFromFeed,
          isMessagesBlocked = _isMessagesBlocked,
          links = _links,
          mainAlbumId = _mainAlbumId,
          mainSection = _mainSection,
          market = _market,
          memberStatus = _memberStatus,
          membersCount = _membersCount,
          place = _place,
          publicDateLabel = _publicDateLabel,
          site = _site,
          startDate = _startDate,
          finishDate = _finishDate,
          status = _status,
          trending = _trending,
          verified = _verified,
          wall = _wall,
          wikiPage = _wikiPage
        )
      }
    }

  implicit lazy val communitycontactsEncoder: Encoder[CommunityContacts] =
    (x: CommunityContacts) => {
      Json.fromFields(
        List(
          "user_id" -> x.userId.asJson,
          "desc"    -> x.desc.asJson,
          "phone"   -> x.phone.asJson,
          "email"   -> x.email.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val communitycontactsDecoder: Decoder[CommunityContacts] =
    Decoder.instance { h =>
      for {
        _userId <- h.get[Int]("user_id")
        _desc   <- h.get[String]("desc")
        _phone  <- h.get[String]("phone")
        _email  <- h.get[String]("email")
      } yield {
        CommunityContacts(userId = _userId, desc = _desc, phone = _phone, email = _email)
      }
    }

  implicit lazy val baninfoEncoder: Encoder[BanInfo] =
    (x: BanInfo) => {
      Json.fromFields(
        List(
          "end_date" -> x.endDate.asJson,
          "comment"  -> x.comment.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val baninfoDecoder: Decoder[BanInfo] =
    Decoder.instance { h =>
      for {
        _endDate <- h.get[Integer]("end_date")
        _comment <- h.get[String]("comment")
      } yield {
        BanInfo(endDate = _endDate, comment = _comment)
      }
    }

  implicit lazy val linkEncoder: Encoder[Link] =
    (x: Link) => {
      Json.fromFields(
        List(
          "text" -> x.text.asJson,
          "url"  -> x.url.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val linkDecoder: Decoder[Link] =
    Decoder.instance { h =>
      for {
        _text <- h.get[String]("text")
        _url  <- h.get[String]("url")
      } yield {
        Link(text = _text, url = _url)
      }
    }

  implicit lazy val coverEncoder: Encoder[Cover] =
    (x: Cover) => {
      Json.fromFields(
        List(
          "enabled" -> x.enabled.asJson,
          "images"  -> x.images.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val coverDecoder: Decoder[Cover] =
    Decoder.instance { h =>
      for {
        _enabled <- h.get[Boolean]("enabled")
        _images  <- h.get[Vector[CommunityImages]]("images")
      } yield {
        Cover(enabled = _enabled, images = _images)
      }
    }

  implicit lazy val communityimagesEncoder: Encoder[CommunityImages] =
    (x: CommunityImages) => {
      Json.fromFields(
        List(
          "url"    -> x.url.asJson,
          "width"  -> x.width.asJson,
          "height" -> x.height.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val communityimagesDecoder: Decoder[CommunityImages] =
    Decoder.instance { h =>
      for {
        _url    <- h.get[String]("url")
        _width  <- h.get[Int]("width")
        _height <- h.get[Int]("height")
      } yield {
        CommunityImages(url = _url, width = _width, height = _height)
      }
    }

  implicit lazy val communitylinkEncoder: Encoder[CommunityLink] =
    (x: CommunityLink) => {
      Json.fromFields(
        List(
          "id"       -> x.id.asJson,
          "url"      -> x.url.asJson,
          "name"     -> x.name.asJson,
          "desc"     -> x.desc.asJson,
          "photo50"  -> x.photo50.asJson,
          "photo100" -> x.photo100.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val communitylinkDecoder: Decoder[CommunityLink] =
    Decoder.instance { h =>
      for {
        _id       <- h.get[Integer]("id")
        _url      <- h.get[String]("url")
        _name     <- h.get[String]("name")
        _desc     <- h.get[String]("desc")
        _photo50  <- h.get[String]("photo50")
        _photo100 <- h.get[String]("photo100")
      } yield {
        CommunityLink(id = _id,
                      url = _url,
                      name = _name,
                      desc = _desc,
                      photo50 = _photo50,
                      photo100 = _photo100)
      }
    }

  implicit lazy val marketEncoder: Encoder[Market] =
    (x: Market) => {
      Json.fromFields(
        List(
          "enabled"       -> x.enabled.asJson,
          "price_min"     -> x.priceMin.asJson,
          "price_max"     -> x.priceMax.asJson,
          "main_album_id" -> x.mainAlbumId.asJson,
          "contact_id"    -> x.contactId.asJson,
          "currency"      -> x.currency.asJson,
          "currency_text" -> x.currencyText.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val marketDecoder: Decoder[Market] =
    Decoder.instance { h =>
      for {
        _enabled      <- h.get[Boolean]("enabled")
        _priceMin     <- h.get[Int]("price_min")
        _priceMax     <- h.get[Int]("price_max")
        _mainAlbumId  <- h.get[Int]("main_album_id")
        _contactId    <- h.get[Int]("contact_id")
        _currency     <- h.get[Currency]("currency")
        _currencyText <- h.get[String]("currency_text")
      } yield {
        Market(enabled = _enabled,
               priceMin = _priceMin,
               priceMax = _priceMax,
               mainAlbumId = _mainAlbumId,
               contactId = _contactId,
               currency = _currency,
               currencyText = _currencyText)
      }
    }

}
