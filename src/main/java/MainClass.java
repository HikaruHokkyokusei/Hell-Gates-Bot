import org.apache.log4j.BasicConfigurator;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;

// When the application is started, the admin must send "run" message from his main account to turn on the bot.

// Maven Run configurations :-
// heroku:deploy

/*Required Environment Variables :-
 *
 * hellgatesBotTokenA   = ?????;
 * hellgatesBotTokenB   = ?????;
 * hellGatesMonoID      = ?????;
 * hellGatesMonoPass    = ?????;
 * PrivateKey           = ?????;
 * PublicKey            = ?????
 * */

public class MainClass {

    // Eth Network Type = "ropsten" or "mainnet"
    private static final String EthNetworkType = "mainnet";
    private static final String ourWallet = "0x57AbCF8F01D08489236a490661aDB85c3aBB47Bc",
            shotWallet = "0xdcCF6EE3977903d541B47F31D5bfD3AED3511C62",
            RTKContractAddress = "0x1F6DEADcb526c4710Cf941872b86dcdfBbBD9211";
    private static final BigInteger joinCost = new BigInteger("250000000000000000000");
    private static final BigInteger shotCost = new BigInteger("10000000000000000000");
    static int minimumNumberOfPlayers = 3;

    public static void main(String[] args) {
        BasicConfigurator.configure();
        disableAccessWarnings();
        System.setProperty("com.google.inject.internal.cglib.$experimental_asm7", "true");

        // Starting Telegram bot and Web3 services
        initialize();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            telegramBotsApi.registerBot(new Hell_Gates_Bot(EthNetworkType, ourWallet, shotWallet, RTKContractAddress, joinCost, shotCost, minimumNumberOfPlayers));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        ApiContextInitializer.init();
    }

    @SuppressWarnings("unchecked")
    private static void disableAccessWarnings() {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }
}

////////////////////////            WORKING STUFF NOT IN USE BUT FOR REFERENCE BELOW            ////////////////////////
//
//
//
//            EthBlockNumber ethBlockNumber = null;
//            EthAccounts ethAccounts;
//            EthGetBalance ethGetBalance;
//            List<Log> events = new ArrayList<>();
//            ethBlockNumber = web3.ethBlockNumber().send();
//            ethAccounts = web3.ethAccounts().send();
//            ethGetBalance = web3.ethGetBalance(ourWallet, DefaultBlockParameter.valueOf("latest")).send();
//            web3j.ethLogFlowable(RTKContractFilter).subscribe(tx -> {
//                System.out.println(tx.getTransactionHash());
//                events.add(tx);
//                System.out.println(tx.getData());
//            });
//
//
//
//          allRTKTransactionEvents.add(RTKToken.getTransferEvents(
//                            web3j.ethGetTransactionReceipt(log.getTransactionHash()).send().getTransactionReceipt().get()).get(0));
//
//
//
//
//            RTKToken.transferEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
//                    .subscribe(event -> {
//                        String hash = Integer.toString(event.hashCode());
//                        System.out.println(hash);
//                        //System.out.println("from: " + event._from + ", to: " + event._to + ", value: " + event._value);
//                        //allRTKTransactionEvents.add(event);
//                    });
//            web3j.ethNewFilter(RTKContractFilter);
//            String inData = web3j.ethGetTransactionByHash(allRTKTransactions.get(2).getTransactionHash()).send().getTransaction().get().getInput();
//            System.out.println(inData);
//
//
//
//            BELOW CODE READS ALL TRANSACTIONS MADE TO A CONTRACT
//
//    ArrayList<org.web3j.protocol.core.methods.response.Log> RTKTransactions = new ArrayList<>();
//    AtomicInteger numberOfTransactionsFetched = new AtomicInteger(0);
//    Disposable disposable = web3j.ethLogFlowable(RTKContractFilter).subscribe(log -> {
//        if(numberOfTransactionsFetched.get() != 0) {
//            String hash = log.getTransactionHash();
//            String prevHash = RTKTransactions.get(numberOfTransactionsFetched.get() - 1).getTransactionHash();
//            if(!hash.equals(prevHash)) {
//                RTKTransactions.add(log);
//                numberOfTransactionsFetched.getAndIncrement();
//                System.out.println(log.getTransactionHash());
//            }
//        } else {
//            RTKTransactions.add(log);
//            numberOfTransactionsFetched.getAndIncrement();
//            System.out.println(log.getTransactionHash());
//        }
//    }, throwable -> {
//        throwable.printStackTrace();
//    });
//
//
//
////////////////////////            WORKING STUFF NOT IN USE BUT FOR REFERENCE ABOVE            ////////////////////////