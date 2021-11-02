package com.ekenya.rnd.offlinebanking.ethereum;
import android.os.Environment;
import android.util.Log;

import org.ethereum.geth.Account;
import org.ethereum.geth.Accounts;
import org.ethereum.geth.Address;
import org.ethereum.geth.Context;
import org.ethereum.geth.EthereumClient;
import org.ethereum.geth.Geth;
import org.ethereum.geth.Header;
import org.ethereum.geth.KeyStore;
import org.ethereum.geth.NewHeadHandler;
import org.ethereum.geth.Node;
import org.ethereum.geth.NodeConfig;
import org.ethereum.geth.NodeInfo;
import org.ethereum.geth.PeerInfo;

import java.io.File;

/**
 * SEE : <link>https://yenhuang.gitbooks.io/blockchain/content/interact-with-private-chain-on-android/create-account-on-android.html</link>
 */
public class GethNodeSample {
    private static final long PRIVATE_CHAIN_NETWORK_ID = 56821;
    private KeyStore keyStore;
    private Account account;

    class PrivateChain {
        public static final String genesis = "{ \"config\": { \"chainId\": 56821, \"homesteadBlock\": 1, \"eip150Block\": 2, \"eip150Hash\": \"0x0000000000000000000000000000000000000000000000000000000000000000\", \"eip155Block\": 3, \"eip158Block\": 3, \"clique\": { \"period\": 3, \"epoch\": 30000 } }, \"nonce\": \"0x0\", \"timestamp\": \"0x59cf588f\", \"extraData\": \"0x0000000000000000000000000000000000000000000000000000000000000000726794b16f6c5b0be0b78d7713a876ed3da8be1a0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\", \"gasLimit\": \"0x47b760\", \"difficulty\": \"0x1\", \"mixHash\": \"0x0000000000000000000000000000000000000000000000000000000000000000\", \"coinbase\": \"0x0000000000000000000000000000000000000000\", \"alloc\": { \"c077f8420d5b6c125897d9c5e21293ff6f77855c\": { \"balance\": \"0x200000000000000000000000000000000000000000000000000000000000000\" } }, \"number\": \"0x0\", \"gasUsed\": \"0x0\", \"parentHash\": \"0x0000000000000000000000000000000000000000000000000000000000000000\" }";
    }

    private void createAccountDemo() throws Exception {
        createKeyStore(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  "/ETH/privatechain/keystore").getPath());
        account = createAccount("a1234567");
        Log.d("main", account.getURL());
        Log.d("main", "account address is " + account.getAddress().getHex());
    }

    private void createKeyStore(String keyStoreFolderPath) {
        keyStore = new KeyStore(keyStoreFolderPath, Geth.LightScryptN, Geth.LightScryptP);
    }

    private Account createAccount(String password) throws Exception {
        return keyStore.newAccount(password);
    }

    /**
     *
     * @throws Exception
     */
    public void startNode2CheckBalance() throws Exception {
        createKeyStore(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "ETH" + "/privatechain/keystore").getPath());
        Accounts accounts = keyStore.getAccounts();
        account = accounts.get(0);

        final Context ctx = new Context();

        NodeConfig nodeConfig = Geth.newNodeConfig();
        nodeConfig.setEthereumNetworkID(PRIVATE_CHAIN_NETWORK_ID);
        nodeConfig.setEthereumGenesis(PrivateChain.genesis);
        nodeConfig.setEthereumDatabaseCache(512);
        nodeConfig.setMaxPeers(10);
        nodeConfig.setWhisperEnabled(true);
        nodeConfig.setEthereumEnabled(true);
        final Node node = Geth.newNode(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "ETH" + "/privatechain").getPath(), nodeConfig);

        try {
            node.start();

            NodeInfo info = node.getNodeInfo();
            Log.d("main", "name is " + info.getName());
            Log.d("main", "address is " + info.getListenerAddress());
            Log.d("main", "protocols is " + info.getProtocols());
            Log.d("main", "ID is " + info.getID());
            Log.d("main", "ListenerPort is " + info.getListenerPort());
            Log.d("main", "DiscoveryPort is " + info.getDiscoveryPort());
            Log.d("main", "IP is " + info.getIP());
            Log.d("main", "enode is " + info.getEnode());

            final EthereumClient ec = node.getEthereumClient();
            Log.d("main", "Latest block: " + ec.getBlockByNumber(ctx, -1).getNumber() + ", syncing");

            NewHeadHandler handler = new NewHeadHandler() {
                @Override
                public void onError(String error) {
                    Log.d("main", "NewHeadHandler: " + error);
                }

                @Override
                public void onNewHead(final Header header) {
                    try {
                        Log.d("main", "#" + header.getNumber() + ": " + header.getHash().getHex().substring(0, 10));
                        PeerInfo peerInfo = node.getPeersInfo().get(0);
                        Log.d("main", "peers amount " + node.getPeersInfo().size() + "; " + peerInfo.getName() + "; " + peerInfo.getID() + "; " + peerInfo.getRemoteAddress());
                        Log.d("main", "balance of android node " + account.getAddress().getHex() + " is " + ec.getBalanceAt(ctx, account.getAddress(), -1).getInt64());
                        Log.d("main", "balance of server node 0x726794b16f6c5b0be0b78d7713a876ed3da8be1a is " + ec.getBalanceAt(ctx, new Address("0x726794b16f6c5b0be0b78d7713a876ed3da8be1a"), -1).getInt64());
                        Log.d("main", "balance of miner2 node 0xc077f8420d5b6c125897d9c5e21293ff6f77855c is " + ec.getBalanceAt(ctx, new Address("0xc077f8420d5b6c125897d9c5e21293ff6f77855c"), -1).getInt64());
                        Log.d("main", "balance of light node 0x52ea0215068f436f837788686aa4b25de1a98f07 is " + ec.getBalanceAt(ctx, new Address("0x52ea0215068f436f837788686aa4b25de1a98f07"), -1).getInt64());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            ec.subscribeNewHead(ctx, handler, 16);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

