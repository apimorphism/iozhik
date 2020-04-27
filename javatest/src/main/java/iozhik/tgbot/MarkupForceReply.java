
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class MarkupForceReply implements KeyboardMarkup {
   ForceReply markup;

   public MarkupForceReply() {}

   public MarkupForceReply(ForceReply v) {
     this.markup = v;
   }

   public MarkupForceReply setMarkup(ForceReply v) {
     this.markup = v;
     return this;
   }

   @JsonValue
   public ForceReply getMarkup() {
     return this.markup;
   }
 }
 