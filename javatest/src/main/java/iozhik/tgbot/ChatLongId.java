
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class ChatLongId implements ChatId {
   long id;

   public ChatLongId() {}

   public ChatLongId(long v) {
     this.id = v;
   }

   public ChatLongId setId(long v) {
     this.id = v;
     return this;
   }

   @JsonValue
   public long getId() {
     return this.id;
   }
 }
 