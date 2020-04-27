
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class MarkupRemoveKeyboard implements KeyboardMarkup {
   ReplyKeyboardRemove markup;

   public MarkupRemoveKeyboard() {}

   public MarkupRemoveKeyboard(ReplyKeyboardRemove v) {
     this.markup = v;
   }

   public MarkupRemoveKeyboard setMarkup(ReplyKeyboardRemove v) {
     this.markup = v;
     return this;
   }

   @JsonValue
   public ReplyKeyboardRemove getMarkup() {
     return this.markup;
   }
 }
 