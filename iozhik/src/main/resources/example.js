namespace iozhik {

    namespace vk {

        SizedPhoto {
            src    : String
            width  : Int
            height : Int
            type   : String
        }

        Photo <5.77 {
            id        : Int
            albumId   : Int
            ownerId   : Int
            userId    : Int
            text      : String
            date      : (useDateTime) UnixTimestamp
            photo75   : String
            photo130  : String
            photo604  : String
            photo807  : String
            photo1280 : String
            photo2560 : String
            width     : Option[Integer]
            height    : Option[Integer]
        }

        Photo {
            id      : Int
            albumId : Int
            ownerId : Int
            userId  : Int
            text    : String
            date    : (useDateTime) UnixTimestamp
            sizes   : Vector[SizedPhoto]
            width   : Option[Integer]
            height  : Option[Integer]
        }

        Video <5.0 {
            vid         : Int
            ownerId     : Int
            title       : String
            description : String
            link        : String
            image       : String
            imageMedium : String
            date        : (useDateTime) UnixTimestamp
            player      : String
        }

        Video >=5.0 {
            id             : Int
            ownerId        : Int
            title          : String
            description    : String
            duration       : Int
            photo130       : String
            photo320       : String
            photo640       : String
            photo800       : String
            photo1280      : String
            firstFrame130  : String
            firstFrame320  : String
            firstFrame640  : String
            firstFrame800  : String
            firstFrame1280 : String
            date           : (useDateTime) UnixTimestamp
            addingDate     : (useDateTime) UnixTimestamp
            views          : Int
            comments       : Int
            player         : String
            platform       : String
            canEdit        : Option[Int]
            canAdd         : Int
            isPrivate      : Option[Int]
            processing     : Option[Int]
            live           : Option[Int]
            upcoming       : String
            isFavorite     : Boolean
        }

        abstract Attachment {
        }

        AttachmentPhoto {
            mix Attachment
            type: "photo"
            photo: Photo
        }

        AttachmentVideo {
            mix Attachment
            type: "video"
            photo: Photo
        }

        Place {
            id        : Int
            title     : String
            latitude  : Number
            longitude : Number
            created   : Int
            icon      : String
            country   : String
            city      : String
        }

        Place <5.80 {
            id        : Int
            title     : String
            latitude  : Number
            longitude : Number
            created   : Int
            icon      : String
            country   : String
            city      : String
        }

        Coordinate {
            latitude  : Float
            longitude : Float
        }

        Geo <5.80 {
            type        : String
            coordinates : String
            place       : Place
        }

        Geo {
            type        : String
            coordinates : Coordinate
            place       : Place
        }

        CommentsThread {
            count           : Int
            items           : Vector[Comment]
            canPost         : Boolean
            showReplyButton : Boolean
            groupsCanPost   : Boolean
        }

        Comment {
            id             : Int
            userId         : Int
            date           : (useDateTime) UnixTimestamp
            text           : String
            replyToUser    : Int
            replyToComment : Int
            attachments    : Vector[Attachment]
            parentsStack   : Vector[Int]
            thread         : CommentsThread
        }

        Message <5.80 {
            id           : Int
            userId       : Int
            fromId       : Int
            date         : (useDateTime) UnixTimestamp
            readState    : Int
            ref          : String
            refSource    : String
            out          : Option[Int]
            title        : String
            body         : String
            geo          : Geo
            attachments  : Vector[Attachment]
            fwdMessages  : Vector[Message]
            emoji        : Int
            important    : Int
            deleted      : Int
            randomId     : Int
        }

        Message {
            id           : Int
            date         : (useDateTime) UnixTimestamp
            peerId       : Int
            fromId       : Int
            text         : String
            randomId     : Int
            ref          : String
            refSource    : String
            attachments  : Vector[Attachment]
            important    : Boolean
            geo          : Geo
            payload      : String
            fwdMessages  : Vector[Message]
            replyMessage : Vector[Message]
        }

        abstract Event {
            groupId : Int
        }

        EventMessageNew {
            mix Event
            type    : "message_new"
            groupId : Int
            object  : Message
        }

        EventMessageReply {
            mix Event
            type    : "message_reply"
            groupId : Int
            object  : Message
        }

        EventMessageEdit {
            mix Event
            type    : "message_edit"
            groupId : Int
            object  : Message
        }

        EventMessageAllow {
            mix Event
            type    : "message_allow"
            groupId : Int
            flatten object : {
                userId : Int
                key    : String
            }
        }

        EventMessageDeny {
            mix Event
            type    : "message_deny"
            groupId : Int
            flatten object : {
                userId : Int
            }
        }

        EventPhotoNew {
            mix Event
            type    : "photo_new"
            groupId : Int
            object  : Photo
        }

        EventPhotoCommentNew {
            mix Event
            type    : "photo_comment_new"
            groupId : Int
            object  : {
                include Comment
                photoId      : Int
                photoOwnerId : Int
            }
        }

        EventPhotoCommentEdit {
            mix Event
            type    : "photo_comment_edit"
            groupId : Int
            object  : {
                include Comment
                photoId      : Int
                photoOwnerId : Int
            }
        }

        EventPhotoCommentRestore {
            mix Event
            type    : "photo_comment_restore"
            groupId : Int
            object: (flatten) {
                include Comment
                photoId      : Int
                photoOwnerId : Int
            }
        }

        EventPhotoCommentDelete {
            mix Event
            type    : "photo_comment_delete"
            groupId : Int
            object : (flatten) {
                ownerId   : Int
                id        : Int
                userId    : Int
                deleterId : Int
                photoId   : Int
            }
        }
    }

}

