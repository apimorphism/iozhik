package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendInvoiceReq {

  private int chatId;

  private String title;

  private String description;

  private String payload;

  private String providerToken;

  private String startParameter;

  private String currency;

  private List<LabeledPrice> prices;

  private String providerData;

  private String photoUrl;

  private Integer photoSize;

  private Integer photoWidth;

  private Integer photoHeight;

  private Boolean needName;

  private Boolean needPhoneNumber;

  private Boolean needEmail;

  private Boolean needShippingAddress;

  private Boolean sendPhoneNumberToProvider;

  private Boolean sendEmailToProvider;

  private Boolean isFlexible;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private InlineKeyboardMarkup replyMarkup;

  public SendInvoiceReq() {}

  public SendInvoiceReq(
      int chatId,
      String title,
      String description,
      String payload,
      String providerToken,
      String startParameter,
      String currency,
      List<LabeledPrice> prices,
      String providerData,
      String photoUrl,
      Integer photoSize,
      Integer photoWidth,
      Integer photoHeight,
      Boolean needName,
      Boolean needPhoneNumber,
      Boolean needEmail,
      Boolean needShippingAddress,
      Boolean sendPhoneNumberToProvider,
      Boolean sendEmailToProvider,
      Boolean isFlexible,
      Boolean disableNotification,
      Integer replyToMessageId,
      InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.title = title;
    this.description = description;
    this.payload = payload;
    this.providerToken = providerToken;
    this.startParameter = startParameter;
    this.currency = currency;
    this.prices = prices;
    this.providerData = providerData;
    this.photoUrl = photoUrl;
    this.photoSize = photoSize;
    this.photoWidth = photoWidth;
    this.photoHeight = photoHeight;
    this.needName = needName;
    this.needPhoneNumber = needPhoneNumber;
    this.needEmail = needEmail;
    this.needShippingAddress = needShippingAddress;
    this.sendPhoneNumberToProvider = sendPhoneNumberToProvider;
    this.sendEmailToProvider = sendEmailToProvider;
    this.isFlexible = isFlexible;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public int getChatId() {
    return this.chatId;
  }

  public SendInvoiceReq setChatId(int v) {
    this.chatId = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public SendInvoiceReq setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getDescription() {
    return this.description;
  }

  public SendInvoiceReq setDescription(String v) {
    this.description = v;
    return this;
  }

  public String getPayload() {
    return this.payload;
  }

  public SendInvoiceReq setPayload(String v) {
    this.payload = v;
    return this;
  }

  public String getProviderToken() {
    return this.providerToken;
  }

  public SendInvoiceReq setProviderToken(String v) {
    this.providerToken = v;
    return this;
  }

  public String getStartParameter() {
    return this.startParameter;
  }

  public SendInvoiceReq setStartParameter(String v) {
    this.startParameter = v;
    return this;
  }

  public String getCurrency() {
    return this.currency;
  }

  public SendInvoiceReq setCurrency(String v) {
    this.currency = v;
    return this;
  }

  public List<LabeledPrice> getPrices() {
    return this.prices;
  }

  public SendInvoiceReq setPrices(List<LabeledPrice> v) {
    this.prices = v;
    return this;
  }

  public Optional<String> getProviderData() {
    return Optional.ofNullable(providerData);
  }

  public SendInvoiceReq setProviderData(String v) {
    this.providerData = v;
    return this;
  }

  public Optional<String> getPhotoUrl() {
    return Optional.ofNullable(photoUrl);
  }

  public SendInvoiceReq setPhotoUrl(String v) {
    this.photoUrl = v;
    return this;
  }

  public Optional<Integer> getPhotoSize() {
    return Optional.ofNullable(photoSize);
  }

  public SendInvoiceReq setPhotoSize(Integer v) {
    this.photoSize = v;
    return this;
  }

  public Optional<Integer> getPhotoWidth() {
    return Optional.ofNullable(photoWidth);
  }

  public SendInvoiceReq setPhotoWidth(Integer v) {
    this.photoWidth = v;
    return this;
  }

  public Optional<Integer> getPhotoHeight() {
    return Optional.ofNullable(photoHeight);
  }

  public SendInvoiceReq setPhotoHeight(Integer v) {
    this.photoHeight = v;
    return this;
  }

  public Optional<Boolean> getNeedName() {
    return Optional.ofNullable(needName);
  }

  public SendInvoiceReq setNeedName(Boolean v) {
    this.needName = v;
    return this;
  }

  public Optional<Boolean> getNeedPhoneNumber() {
    return Optional.ofNullable(needPhoneNumber);
  }

  public SendInvoiceReq setNeedPhoneNumber(Boolean v) {
    this.needPhoneNumber = v;
    return this;
  }

  public Optional<Boolean> getNeedEmail() {
    return Optional.ofNullable(needEmail);
  }

  public SendInvoiceReq setNeedEmail(Boolean v) {
    this.needEmail = v;
    return this;
  }

  public Optional<Boolean> getNeedShippingAddress() {
    return Optional.ofNullable(needShippingAddress);
  }

  public SendInvoiceReq setNeedShippingAddress(Boolean v) {
    this.needShippingAddress = v;
    return this;
  }

  public Optional<Boolean> getSendPhoneNumberToProvider() {
    return Optional.ofNullable(sendPhoneNumberToProvider);
  }

  public SendInvoiceReq setSendPhoneNumberToProvider(Boolean v) {
    this.sendPhoneNumberToProvider = v;
    return this;
  }

  public Optional<Boolean> getSendEmailToProvider() {
    return Optional.ofNullable(sendEmailToProvider);
  }

  public SendInvoiceReq setSendEmailToProvider(Boolean v) {
    this.sendEmailToProvider = v;
    return this;
  }

  public Optional<Boolean> getIsFlexible() {
    return Optional.ofNullable(isFlexible);
  }

  public SendInvoiceReq setIsFlexible(Boolean v) {
    this.isFlexible = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendInvoiceReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendInvoiceReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendInvoiceReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendInvoiceReq)) return false;

    SendInvoiceReq that = (SendInvoiceReq) thatObj;

    return this.chatId == that.chatId
        && this.title.equals(that.title)
        && this.description.equals(that.description)
        && this.payload.equals(that.payload)
        && this.providerToken.equals(that.providerToken)
        && this.startParameter.equals(that.startParameter)
        && this.currency.equals(that.currency)
        && this.prices.equals(that.prices)
        && this.providerData.equals(that.providerData)
        && this.photoUrl.equals(that.photoUrl)
        && this.photoSize.equals(that.photoSize)
        && this.photoWidth.equals(that.photoWidth)
        && this.photoHeight.equals(that.photoHeight)
        && this.needName.equals(that.needName)
        && this.needPhoneNumber.equals(that.needPhoneNumber)
        && this.needEmail.equals(that.needEmail)
        && this.needShippingAddress.equals(that.needShippingAddress)
        && this.sendPhoneNumberToProvider.equals(that.sendPhoneNumberToProvider)
        && this.sendEmailToProvider.equals(that.sendEmailToProvider)
        && this.isFlexible.equals(that.isFlexible)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendInvoiceReq{"
        + "chatId="
        + this.chatId
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + ','
        + "payload="
        + '\''
        + this.payload
        + '\''
        + ','
        + "providerToken="
        + '\''
        + this.providerToken
        + '\''
        + ','
        + "startParameter="
        + '\''
        + this.startParameter
        + '\''
        + ','
        + "currency="
        + '\''
        + this.currency
        + '\''
        + ','
        + "prices="
        + this.prices
        + ','
        + "providerData="
        + '\''
        + this.providerData
        + '\''
        + ','
        + "photoUrl="
        + '\''
        + this.photoUrl
        + '\''
        + ','
        + "photoSize="
        + this.photoSize
        + ','
        + "photoWidth="
        + this.photoWidth
        + ','
        + "photoHeight="
        + this.photoHeight
        + ','
        + "needName="
        + this.needName
        + ','
        + "needPhoneNumber="
        + this.needPhoneNumber
        + ','
        + "needEmail="
        + this.needEmail
        + ','
        + "needShippingAddress="
        + this.needShippingAddress
        + ','
        + "sendPhoneNumberToProvider="
        + this.sendPhoneNumberToProvider
        + ','
        + "sendEmailToProvider="
        + this.sendEmailToProvider
        + ','
        + "isFlexible="
        + this.isFlexible
        + ','
        + "disableNotification="
        + this.disableNotification
        + ','
        + "replyToMessageId="
        + this.replyToMessageId
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
