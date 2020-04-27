
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class MarkupReplyKeyboard implements KeyboardMarkup {
   ReplyKeyboardMarkup markup;

   public MarkupReplyKeyboard() {}

   public MarkupReplyKeyboard(ReplyKeyboardMarkup v) {
     this.markup = v;
   }

   public MarkupReplyKeyboard setMarkup(ReplyKeyboardMarkup v) {
     this.markup = v;
     return this;
   }

   @JsonValue
   public ReplyKeyboardMarkup getMarkup() {
     return this.markup;
   }
 }
 