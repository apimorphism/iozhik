
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class InputPartFile implements IFile {
   java.io.File file;

   public InputPartFile() {}

   public InputPartFile(java.io.File v) {
     this.file = v;
   }

   public InputPartFile setFile(java.io.File v) {
     this.file = v;
     return this;
   }

   @JsonValue
   public java.io.File getFile() {
     return this.file;
   }
 }
 