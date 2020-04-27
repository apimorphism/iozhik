
 package iozhik.tgbot;

 import com.fasterxml.jackson.annotation.JsonValue;

 public class InputLinkFile implements IFile {
   String file;

   public InputLinkFile() {}

   public InputLinkFile(String v) {
     this.file = v;
   }

   public InputLinkFile setFile(String v) {
     this.file = v;
     return this;
   }

   @JsonValue
   public String getFile() {
     return this.file;
   }
 }
 