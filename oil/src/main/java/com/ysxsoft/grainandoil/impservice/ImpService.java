package com.ysxsoft.grainandoil.impservice;

import com.ysxsoft.grainandoil.modle.AboutMyBean;
import com.ysxsoft.grainandoil.modle.AcountSafeBean;
import com.ysxsoft.grainandoil.modle.AddAddressBean;
import com.ysxsoft.grainandoil.modle.AddCardBean;
import com.ysxsoft.grainandoil.modle.AgencyListBean;
import com.ysxsoft.grainandoil.modle.AgreementBean;
import com.ysxsoft.grainandoil.modle.AliPayBean;
import com.ysxsoft.grainandoil.modle.AliPayRechargeBean;
import com.ysxsoft.grainandoil.modle.BalanceMoneyBean;
import com.ysxsoft.grainandoil.modle.BankCardListBean;
import com.ysxsoft.grainandoil.modle.BindingPhoneNumBean;
import com.ysxsoft.grainandoil.modle.CheckCodeBean;
import com.ysxsoft.grainandoil.modle.ClassifyDataBean;
import com.ysxsoft.grainandoil.modle.CollectsListBean;
import com.ysxsoft.grainandoil.modle.CustomerPhoneNumBean;
import com.ysxsoft.grainandoil.modle.DelMyAreaBean;
import com.ysxsoft.grainandoil.modle.DeleteCardBean;
import com.ysxsoft.grainandoil.modle.DeleteHistoryBean;
import com.ysxsoft.grainandoil.modle.DeleteShopCardBean;
import com.ysxsoft.grainandoil.modle.DeleteCollectsBean;
import com.ysxsoft.grainandoil.modle.EditorAddressBean;
import com.ysxsoft.grainandoil.modle.FactorListBean;
import com.ysxsoft.grainandoil.modle.ForgetModifyPwdBean;
import com.ysxsoft.grainandoil.modle.GetGoodsAddressBean;
import com.ysxsoft.grainandoil.modle.GradeIntroduceBean;
import com.ysxsoft.grainandoil.modle.HelpCenterBean;
import com.ysxsoft.grainandoil.modle.HomeClassifyBean;
import com.ysxsoft.grainandoil.modle.HomeDialogBean;
import com.ysxsoft.grainandoil.modle.HomeLunBoBean;
import com.ysxsoft.grainandoil.modle.HotGoodsBean;
import com.ysxsoft.grainandoil.modle.IsTrueAddressBean;
import com.ysxsoft.grainandoil.modle.LauncherImgBean;
import com.ysxsoft.grainandoil.modle.LiftingAmountBean;
import com.ysxsoft.grainandoil.modle.LoginBean;
import com.ysxsoft.grainandoil.modle.LookedMessageBean;
import com.ysxsoft.grainandoil.modle.MessageListBean;
import com.ysxsoft.grainandoil.modle.ModifyCardBean;
import com.ysxsoft.grainandoil.modle.ModifySexBean;
import com.ysxsoft.grainandoil.modle.ModifyTradePwdBean;
import com.ysxsoft.grainandoil.modle.ModifyUserNameBean;
import com.ysxsoft.grainandoil.modle.MyBillNumBean;
import com.ysxsoft.grainandoil.modle.MyMsgBean;
import com.ysxsoft.grainandoil.modle.PayBalanceBean;
import com.ysxsoft.grainandoil.modle.ProductListResponse;
import com.ysxsoft.grainandoil.modle.QQLoginBean;
import com.ysxsoft.grainandoil.modle.QQNumberBean;
import com.ysxsoft.grainandoil.modle.ReMoenyBean;
import com.ysxsoft.grainandoil.modle.RecommendBean;
import com.ysxsoft.grainandoil.modle.RegisterBean;
import com.ysxsoft.grainandoil.modle.SearchHistoryBean;
import com.ysxsoft.grainandoil.modle.SearchRecommendBean;
import com.ysxsoft.grainandoil.modle.SecondHomeBean;
import com.ysxsoft.grainandoil.modle.SendMessageBean;
import com.ysxsoft.grainandoil.modle.SesarchBean;
import com.ysxsoft.grainandoil.modle.ShopCardBalanceBean;
import com.ysxsoft.grainandoil.modle.ShopCardBean;
import com.ysxsoft.grainandoil.modle.SystemDetialBean;
import com.ysxsoft.grainandoil.modle.UpdataVersionBean;
import com.ysxsoft.grainandoil.modle.UploadHeadImgBean;
import com.ysxsoft.grainandoil.modle.WalletDetailBean;
import com.ysxsoft.grainandoil.modle.WithdrawBean;
import com.ysxsoft.grainandoil.modle.WxPayBean;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ImpService {
    /**
     * 发送短信验证码
     */
    @POST("index/getcode")
    Observable<SendMessageBean> sendMessage(@Query("phone") String phone);

    /**
     * 登录
     *
     * @param mobile 手机号
     * @param code   密码
     */
    @POST("index/index")
    Observable<LoginBean> Login(@Query("mobile") String mobile,
                                @Query("code") String code);

    /**
     * 注册
     *
     * @param mobile   手机号
     * @param password 密码
     * @param code     验证码
     */
    @POST("index/registers")
    Observable<RegisterBean> Register(@Query("mobile") String mobile,
                                      @Query("password") String password,
//                                      @Query("invitation") String invitation,
                                      @Query("code") String code);

    //忘记密码、修改密码接口
    @POST("index/forgetPwd")
    Observable<ForgetModifyPwdBean> ForgetModifyPwd(@Query("mobile") String mobile,
                                                    @Query("newpasssword") String password,
                                                    @Query("code") String code);

    //绑定手机号码
    @POST("index/phone")
    Observable<BindingPhoneNumBean> BindingPhoneNum(@Query("mobile") String mobile,
                                                    @Query("code") String code,
                                                    @Query("password") String password,
                                                    @Query("uid") String uid,
                                                    @Query("invicode") String invicode);

    //我的
    @POST("index/getMe")
    Observable<MyMsgBean> MyMsg(@Query("uid") String uid);

    //我的 编辑 上传头像
    @Multipart
    @POST("index/avatar")
    Observable<UploadHeadImgBean> UploadHeadImg(@Query("user_id") String user_id,
                                                @Part MultipartBody.Part avatar);

    /**
     * 我的 编辑 修改性别
     *
     * @param uid  用户id
     * @param type 1是男2是女
     */
    @POST("index/sex")
    Observable<ModifySexBean> ModifySex(@Query("uid") String uid,
                                        @Query("type") String type);

    //我的 编辑 修改用户名
    @POST("index/username")
    Observable<ModifyUserNameBean> ModifyUserName(@Query("uid") String uid,
                                                  @Query("username") String username);

    //设置 账户安全 交易密码
    @POST("index/forgetPwds")
    Observable<ModifyTradePwdBean> ModifyTradePwd(@Query("uid") String uid,
                                                  @Query("newtradepassword") String newtradepassword);

    //设置 账户安全 修改手机号
    @POST("index/mobile")
    Observable<ModifyTradePwdBean> ModifyPhoneNum(@Query("uid") String uid,
                                                  @Query("newmobile") String newmobile,
                                                  @Query("code") String code);

    //设置 账户安全
    @POST("index/pay")
    Observable<AcountSafeBean> AcountSafe(@Query("uid") String uid);

    //设置 客服电话
    @POST("index/kfphone")
    Observable<CustomerPhoneNumBean> CustomerPhoneNum();

    //启动图接口
    @POST("index/start")
    Observable<LauncherImgBean> LauncherImg();

    // 关于平台
    @POST("index/content")
    Observable<AboutMyBean> AboutMy();

    // 帮助中心
    @POST("index/assist")
    Observable<HelpCenterBean> HelpCenter();

    // 首页轮播
    @POST("index/banner")
    Observable<HomeLunBoBean> HomeLunBo();

    // 首页八宫格分类
    @POST("index/brand")
    Observable<HomeClassifyBean> HomeClassify();

    // 分类
    @POST("index/classify")
    Observable<ClassifyDataBean> ClassifyData();

    //  收货地址列表
    @POST("index/getMyArea")
    Observable<GetGoodsAddressBean> getGoodsAddressData(@Query("uid") String uid,
                                                        @Query("page") String page);

    //  添加收货地址
    @POST("index/site")
    Observable<AddAddressBean> AddAddressData(@Query("uid") String uid,
                                              @Query("provice") String provice,
                                              @Query("city") String city,
                                              @Query("area") String area,
                                              @Query("address") String address,
                                              @Query("phone") String phone,
                                              @Query("consignee") String consignee,
                                              @Query("is_ture") String is_ture);

    //  编辑收货地址
    @POST("index/editMyArea")
    Observable<EditorAddressBean> EditorAddressData(@Query("uid") String uid,
                                                    @Query("aid") String aid,
                                                    @Query("provice") String provice,
                                                    @Query("city") String city,
                                                    @Query("area") String area,
                                                    @Query("address") String address,
                                                    @Query("phone") String phone,
                                                    @Query("linkname") String linkname,
                                                    @Query("is_ture") String is_ture);

    //  地址 设置为默认
    @POST("index/isTure")
    Observable<IsTrueAddressBean> IsTrueAddressData(@Query("uid") String uid,
                                                    @Query("aid") String aid,
                                                    @Query("is_ture") String is_ture);

    //  地址 设置为默认
    @POST("index/delMyArea")
    Observable<DelMyAreaBean> DelMyAreaData(@Query("aid") String aid);

    //  我的 银行卡列表
    @POST("index/cardlist")
    Observable<BankCardListBean> BankCardListData(@Query("uid") String uid,
                                                  @Query("page") String page);

    //  我的 添加银行卡
    @POST("index/addcard")
    Observable<AddCardBean> AddCardData(@Query("uid") String uid,
                                        @Query("realnames") String realnames,
                                        @Query("idcard") String idcard,
                                        @Query("houses") String houses,
                                        @Query("phone") String phone,
                                        @Query("number") String number,
                                        @Query("code") String code);

    //  我的 修改银行卡
    @POST("index/addcards")
    Observable<ModifyCardBean> ModifyCardData(@Query("uid") String uid,
                                              @Query("pid") String pid,
                                              @Query("realnames") String realnames,
                                              @Query("idcard") String idcard,
                                              @Query("houses") String houses,
                                              @Query("phone") String phone,
                                              @Query("number") String number,
                                              @Query("code") String code);

    //  我的 银行卡删除
    @POST("index/carddelete")
    Observable<DeleteCardBean> DeleteCardData(@Query("pid") String pid);

    //  首页 搜索 type 1是综合2是销量3是价格升序4是降序
    @POST("index/searchs")
    Observable<SesarchBean> SesarchData(@Query("uid") String uid,
                                        @Query("names") String names,
                                        @Query("page") String page,
                                        @Query("type") String type);

    // 首页 热卖商品
    @POST("index/hots")
    Observable<HotGoodsBean> HotGoodsData();

    // 首页 推荐
    @POST("index/recommend")
    Observable<RecommendBean> RecommendData();

    //  根据商品的bid搜索 type 1是综合2是销量3是价格升序4是降序
    @POST("index/searchs")
    Observable<SesarchBean> BidSearchData(@Query("bid") String bid,
                                          @Query("page") String page,
                                          @Query("type") String type);

    //  搜索页 推荐
    @POST("index/searchRecommend")
    Observable<SearchRecommendBean> SearchRecommendData();

    //  搜索页 搜索历史记录
    @POST("index/searchRecommends")
    Observable<SearchHistoryBean> SearchHistoryData(@Query("uid") String uid);

    //  搜索页 清除搜索历史
    @POST("index/searchDelete")
    Observable<DeleteHistoryBean> DeleteHistoryData(@Query("uid") String uid);

    //  购物车列表
    @POST("index/goodsCar")
    Observable<ShopCardBean> ShopCardData(@Query("uid") String uid);

    //  购物车全部删除
    @POST("index/delectcars")
    Observable<DeleteShopCardBean> DeleteShopCardData(@Query("uid") String uid);
    //  购物车部分删除

    @POST("index/delectcar")
    Observable<DeleteShopCardBean> SectionDeleteShopCardData(@Query("cid") String cid,
                                                             @Query("uid") String uid);
    //  购物车结算

    @POST("index/close")
    Observable<ShopCardBalanceBean> ShopCardBalanceData(@Query("cid") String cid,
                                                        @Query("uid") String uid,
                                                        @Query("num") String num,
                                                        @Query("price") String price);
    //  余额支付

    @POST("index/balance")
    Observable<PayBalanceBean> PayBalanceData(@Query("uid") String uid,
                                              @Query("pid") String pid,
                                              @Query("tradepassword") String tradepassword);

    //  余额
    @POST("index/moneys")
    Observable<BalanceMoneyBean> BalanceMoneyData(@Query("uid") String uid);

    //  支付宝支付
    @POST("index/payOrder")
    Observable<AliPayBean> AliPayData(@Query("uid") String uid,
                                      @Query("pid") String pid);

    //  提现
    @POST("index/withdraw")
    Observable<WithdrawBean> WithdrawData(@Query("uid") String uid,
                                          @Query("num") String num,
                                          @Query("psd") String psd,
                                          @Query("bid") String bid, @Query("type") String type);


    //  钱包明细  type 1是充值2提现3消费4返利
    @POST("index/detail")
    Observable<WalletDetailBean> WalletDetailData(@Query("uid") String uid,
                                                  @Query("type") String type,
                                                  @Query("page") String page);

    //  收藏列表
    @POST("index/collects")
    Observable<CollectsListBean> CollectsListData(@Query("uid") String uid);

    //  删除收藏
    @POST("index/collectdelte")
    Observable<DeleteCollectsBean> DeleteCollectsData(@Query("pid") String pid);

    //支付宝充值
    @POST("index/payOrders")
    Observable<AliPayRechargeBean> AliPayRechargeData(@Query("uid") String uid,
                                                      @Query("num") String num,
                                                      @Query("type") String type);

    //QQ第三方登录
    @POST("index/qqlog")
    Observable<QQLoginBean> QQLoginData(@Query("openid") String openid,
                                        @Query("nickname") String nickname,
                                        @Query("avatar") String avatar,
                                        @Query("sex") String sex);

    //消息列表
    @POST("index/message")
    Observable<MessageListBean> MessageListData(@Query("uid") String uid,
                                                @Query("page") String page);

    //消息已读
    @POST("index/sysMsgDea")
    Observable<LookedMessageBean> LookedMessageData(@Query("uid") String uid,
                                                    @Query("sid") String sid);

    //会员协议
    @POST("index/member")
    Observable<AgreementBean> AgreementData();

    //系统 消息详情
    @POST("index/details")
    Observable<SystemDetialBean> SystemDetialData(@Query("sid") String sid);

    //校验验证码
    @POST("index/Pwd")
    Observable<CheckCodeBean> CheckCodeData(@Query("mobile") String mobile,
                                            @Query("code") String code);

    //微信支付
    @POST("index/wxpay")
    Observable<WxPayBean> WxPayData(@Query("uid") String uid,
                                    @Query("pid") String pid);
    //首页二级分类

    @POST("index/brands")
    Observable<SecondHomeBean> getSecondHomeData(@Query("page") String page,
                                                 @Query("cid") String cid);

    //微信充值
    @POST("index/wxpays")
    Observable<WxPayBean> WxPaysData(@Query("uid") String uid,
                                     @Query("num") String num,
                                     @Query("type") String type);

    //官方QQ
    @POST("index/qqNumber")
    Observable<QQNumberBean> QQNumberData();

    //收益列表
    @POST("index/agencyList")
    Observable<AgencyListBean> AgencyList(@Query("uid") String uid,
                                          @Query("page") String page);

    //充值金额——等级列表、等级介绍
    @POST("index/calss")
    Observable<GradeIntroduceBean> GradeIntroduce();

    /**
     * 首页商品分类菜单 商品列表
     *
     * @param map
     * @return
     */
    @POST("index/clicks")
    Observable<ProductListResponse> getProductList(@QueryMap HashMap<String, String> map);

    @POST("index/clicksss")
    Observable<ProductListResponse> getProductDatas(@QueryMap HashMap<String, String> map);


    @POST("index/regulation")
    Observable<HomeDialogBean> getRegulation();

    @POST("index/bill")
    Observable<ReMoenyBean> getReMoeny(@Query("uid") String uid);


    @POST("index/bills")
    Observable<MyBillNumBean> MyBillNum(@Query("uid") String uid);


    @POST("index/promotes")
    Observable<LiftingAmountBean> LiftingAmount(@Query("uid") String uid,
                                                @Query("class") String cls);

    @POST("index/banben")
    Observable<UpdataVersionBean> UpdataVersion();

    @POST("index/factorList")
    Observable<FactorListBean> FactorList(@Query("uid") String uid,
                                          @Query("page") String page);


}
