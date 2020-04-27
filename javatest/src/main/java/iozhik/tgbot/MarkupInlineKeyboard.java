
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class MarkupInlineKeyboard implements KeyboardMarkup {
   InlineKeyboardMarkup markup;

   public MarkupInlineKeyboard() {}

   public MarkupInlineKeyboard(InlineKeyboardMarkup v) {
     this.markup = v;
   }

   public MarkupInlineKeyboard setMarkup(InlineKeyboardMarkup v) {
     this.markup = v;
     return this;
   }

   @JsonValue
   public InlineKeyboardMarkup getMarkup() {
     return this.markup;
   }
 }
 