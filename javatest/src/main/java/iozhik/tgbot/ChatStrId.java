
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class ChatStrId implements ChatId {
   String id;

   public ChatStrId() {}

   public ChatStrId(String v) {
     this.id = v;
   }

   public ChatStrId setId(String v) {
     this.id = v;
     return this;
   }

   @JsonValue
   public String getId() {
     return this.id;
   }
 }
 