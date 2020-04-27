package iozhik.vk.groups

trait Layer[F[_]] {
  def get(x: GetReq): F[GetRes]
  def getLongPollServer(x: GetLongPollServerReq): F[GetLongPollServerRes]
}
