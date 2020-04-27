package iozhik;

import com.mashape.unirest.http.Unirest;
import iozhik.tgbot.*;
import iozhik.tgbot.client.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LongPollBotSpec extends TestCase {

    @Test
    public void testTheTest() {
        assert(true);
    }

    @Test
    public void testLongPollEchoBot() throws Exception {
        // @oderskybot
        String botToken = "814745401:AAF_LfzUoJjrbQfwidP94LRrTYLB6lnj6sA";
        String baseUrl = "https://api.telegram.org/bot" + botToken;

        MyObjectMapper myObjectMapper = new MyObjectMapper();
        Unirest.setObjectMapper(myObjectMapper);

        AtomicInteger offset = new AtomicInteger(0);

        Api api = new ApiUnirestImpl(baseUrl);

        while (true) {
            // TODO: main thread blocking part, do async
            GetUpdatesRes updates = api.getUpdates(
                new GetUpdatesReq()
                    .setOffset(offset.get())
                    .setLimit(1)
                    .setTimeout(10)
            );

            updates.getResult().forEach( upd ->
                upd.getMessage().map( msg -> {

                    // Message with keyboard
                    SendMessageReq sendMessageReq
                        = new SendMessageReq()
                            .setChatId( new ChatLongId(msg.getChat().getId()) )
                            .setText( msg.getText().orElse("Write something") )
                            .setReplyMarkup(
                                new MarkupReplyKeyboard()
                                    .setMarkup(
                                        new ReplyKeyboardMarkup()
                                            .setResizeKeyboard(true)
                                            .setKeyboard(
                                                asList(
                                                    asList(
                                                        new KeyboardButton().setText("1"),
                                                        new KeyboardButton().setText("2"),
                                                        new KeyboardButton().setText("3"),
                                                        new KeyboardButton().setText("4")
                                                    ),
                                                    asList(
                                                        new KeyboardButton().setText("5"),
                                                        new KeyboardButton().setText("6"),
                                                        new KeyboardButton().setText("7"),
                                                        new KeyboardButton().setText("8")
                                                    ),
                                                    asList(
                                                        new KeyboardButton().setText("9"),
                                                        new KeyboardButton().setText("10"),
                                                        new KeyboardButton().setText("11"),
                                                        new KeyboardButton().setText("12"))))));


                    // Location
                    SendLocationReq sendLocationReq
                            = new SendLocationReq()
                                .setChatId( new ChatLongId(msg.getChat().getId()) )
                                .setLatitude( 56.8519f )
                                .setLongitude( 60.6122f );

                    // Photo
                    SendPhotoReq sendPhotoReq
                            = new SendPhotoReq()
                                .setChatId( new ChatLongId(msg.getChat().getId()) )
                                .setPhoto( new InputLinkFile("https://i0.bloomjoy.systems/wp-content/uploads/2018/09/what-you-need-to-know-about-the-nice-girl-whos-unbelievably-tough-768x422.png") )
                                .setCaption( "sample photo from url" );

                    // Photo from file
                    SendPhotoReq sendPhotoReqFile
                            = new SendPhotoReq()
                                .setChatId( new ChatLongId(msg.getChat().getId()) )
                                .setPhoto( new InputPartFile(new java.io.File("~/s1.png")) )
                                .setCaption( "sample photo from file" );

                    try { {
                        SendLocationRes res = api.sendLocation(sendLocationReq);
                        System.out.println(res.toString());
                    } {
                        SendMessageRes res = api.sendMessage(sendMessageReq);
                        System.out.println(res.toString());
                    } {
                        SendPhotoRes res = api.sendPhoto(sendPhotoReq);
                        System.out.println(res.toString());
                    } {
                        SendPhotoRes res = api.sendPhoto(sendPhotoReqFile);
                        System.out.println(res.toString());
                    } } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                    return true;
                })
            );

            updates
                .getResult()
                .stream()
                .map(Update::getUpdateId)
                .max(Integer::compareTo)
                .ifPresent(x -> offset.set(x+1));
        }
    }

    public static <T> List<T> asList(T ... items) {
        List<T> list = new ArrayList<T>();
        for (T item : items) {
            list.add(item);
        }

        return list;
    }
}
