package com.lsxy.area.agent;

/**
 * Created by tandy on 16/12/10.
 */
//@Component
//public class AreaClientBindCallBack implements ClientBindCallback {
//    private static final Logger logger = LoggerFactory.getLogger(AreaClientBindCallBack.class);
//    @Autowired
//    private ClientSessionContext sessionContext;
//
//    @Autowired
//    private RPCCaller rpcCaller;
//
//    @Autowired
//    private RedisCacheService redisCacheService;
//
//    @Override
//    public void doCallback(Session session) throws Exception {
//        logger.info("连接服务器成功,hahhaaha");
////        String value = redisCacheService.get("increase_tid");
////        System.out.println(value);
////        int threads = 20;
////        int count = 10000000;
////        doTestRequest(threads,count,session);
//    }
//
//
//
//    public void doTestRequest(int threads,int count,Session session){
//
//        for(int i=0;i<threads ; i++){
//            final int k = i;
//            Thread t = new Thread(new RunTask(i,session));
//            t.setName("test-"+i);
//            t.start();
//        }
//    }
//
//    class RunTask implements Runnable{
//        private int tNo;
//        private Session session;
//        public RunTask(int t,Session session){
//            this.tNo = t;
//            this.session = session;
//        }
//        @Override
//        public void run() {
//            int j = 0;
//            while(true){
//                try {
//                    RPCRequest request = RPCRequest.newRequest("MN_CH_TEST_ECHO","t="+tNo+"&c="+(++j));
//                    session.write(request);
//                    Thread.currentThread().sleep(10);
//                } catch (Exception ex) {
//                    logger.error("RPC 异常",ex);
//                }
//            }
//        }
//    }
//
//}
