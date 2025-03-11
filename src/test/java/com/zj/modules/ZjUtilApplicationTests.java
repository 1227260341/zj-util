package com.zj.modules;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDDerivationException;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException.MnemonicLengthException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script.ScriptType;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;

import com.alibaba.fastjson.JSON;
import com.zj.modules.util.AnalysisIdCardUtil;
import com.zj.modules.util.OkHttpClientUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZjUtilApplicationTests {
//	OkHttpConfiguration.java
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testAddress()  {
		
//		String addr = "山东省潍坊市诸城市舜王街道武装部北东臧家庄家和超市";
//		String addr = "广东省韶关市浈江区站南路7号邮政综合楼206华启贸易区";
		String addr = "重庆重庆市綦江区綦江区世纪花城二期（重庆市綦江区九龙大道）";
		Map<String, String> addressResolution = AnalysisIdCardUtil.addressResolution(addr);
		String province = addressResolution.get("province");
		String city = addressResolution.get("city");
		String area = addressResolution.get("area");
		String town = addressResolution.get("town");
		String detail = addressResolution.get("detail");
		System.out.println("testAddress" + province +  "," + city + "," + area + "," + town + "," + detail);
	}
	
	/*********区块链*******/
//	@Test
	public void xCreateWalletAccount()  {
        String method = "POST";
//        String requestPath = "/api/v5/account/balance?ccy=BTC";
        String requestPath = "/api/v5/wallet/account/create-wallet-account";
//        String body = "{\"addresses\":[{\"chainIndex\": 1,\"address\": \"34234\"}]}"; // GET请求通常无请求体
        String secretKey = "863C8BC6DFF87340F2A90792519069F7";
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map<String, String>> addressesList = new ArrayList<Map<String, String>>();
        Map<String, String> addressesBtc = new HashMap<String, String>();
        addressesBtc.put("chainIndex", "0");
        addressesBtc.put("address", "16jdxNFyEAjscJKK9oqWqyzAKDSBDUaCBN");
        addressesList.add(addressesBtc);
        Map<String, String> addressesEth = new HashMap<String, String>();
        addressesEth.put("chainIndex", "1");
        addressesEth.put("address", "0x72e296488f2cc91483b7449ced7a22bfd20f6efb");
        addressesList.add(addressesEth);
//        Map<String, String> addressesBsc = new HashMap<String, String>();
//        addressesBsc.put("chainIndex", "");
//        addressesBsc.put("address", "");
//        Map<String, String> addressesTron = new HashMap<String, String>();
//        addressesTron.put("chainIndex", "");
//        addressesTron.put("address", "");
//        Map<String, String> addressesUsdtErc20 = new HashMap<String, String>();
//        addressesUsdtErc20.put("chainIndex", "");
//        addressesUsdtErc20.put("address", "");
//        Map<String, String> addressesSol = new HashMap<String, String>();
//        addressesSol.put("chainIndex", "");
//        addressesSol.put("address", "");
        params.put("addresses", addressesList);
        String body = JSON.toJSONString(params);        

         DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(java.time.ZoneId.of("UTC"));
         String formattedTime = ISO_8601_FORMATTER.format(Instant.now());
        
        String signature = generateSignature(formattedTime, method, requestPath, body, secretKey);
        System.out.println("OK-ACCESS-SIGN: " + signature);
        System.out.println("UTC Date: " + formattedTime);
        
        Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put("OK-ACCESS-KEY", "ce74f7c9-02b0-4582-bafd-3d60ac6df7cb");
		httpHeaders.put("OK-ACCESS-SIGN", signature);
		httpHeaders.put("OK-ACCESS-TIMESTAMP", formattedTime);
		httpHeaders.put("OK-ACCESS-PASSPHRASE", "Whatwallet1.");
		httpHeaders.put("OK-ACCESS-PROJECT", "5c7da974caeceeea0b56aa6e5d41965d");
//		String doPost2 = OkHttpClientUtil.doPost("https://www.okx.com/api/v5/wallet/account/create-wallet-account");
//		HttpUtil.requestHttpForPostService(body, "https://www.okx.com/api/v5/wallet/account/create-wallet-account");
		String doPost = OkHttpClientUtil.doPostJson("https://www.okx.com/api/v5/wallet/account/create-wallet-account", body, httpHeaders);
        
        System.out.println("end " + doPost);
        System.out.println("---------");
	}
	
//	@Test
	public void xCreateWallet()  {
		 try {
			// 1. 生成随机的助记词（BIP-39）
			SecureRandom secureRandom = new SecureRandom();
			byte[] entropy = new byte[16]; // 128位熵，生成12个单词
			secureRandom.nextBytes(entropy);
			List<String> mnemonic = MnemonicCode.INSTANCE.toMnemonic(entropy);

			System.out.println("助记词: " + String.join(" ", mnemonic));

//        DeterministicSeed seed = new DeterministicSeed(secureRandom, 128, ""); 
			// 2. 从助记词生成种子（BIP-39）
			DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", System.currentTimeMillis());
			byte[] seedBytes = seed.getSeedBytes();
			
			// 3. 创建 HD 钱包（BIP-44）
			// ==========  创建主密钥 ==========
			DeterministicKey rootKey  = HDKeyDerivation.createMasterPrivateKey(seedBytes);
			// ========== 3. 构建确定性层级 ==========
			DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(rootKey);

			// 4. 定义 BIP-44 路径（例如：m/44'/60'/0'/0/0 用于以太坊）
//        String path = "M/44H/60H/0H/0/0"; // OKX 可能使用标准路径
			List<ChildNumber> bip44Path = new ArrayList<ChildNumber>();
			bip44Path.add(new ChildNumber(44, true));   // purpose' (BIP-44)
			bip44Path.add(new ChildNumber(60, true));    // coin_type' (60 = ETH)
			bip44Path.add(new ChildNumber(0, true));     // account'
			bip44Path.add(ChildNumber.ZERO);             // change (0=外部地址)
			bip44Path.add(ChildNumber.ZERO);             // address_index
			// ========== 逐层派生密钥 ==========
			List<ChildNumber> currentPath = new ArrayList<>();
			DeterministicKey currentKey = rootKey;
			for (ChildNumber childNum : bip44Path) {
			    currentKey = deterministicHierarchy.deriveChild(
			        currentPath,          // 父路径
			        false,                 // createParent（不需要创建父节点）
			        false,                 // createChildNumber（不需要记录子编号）
			        childNum               // 子索引
			    );
			    currentPath.add(childNum); // 更新当前路径
			}
			
			// ========== 6. 生成以太坊地址 ==========
			// 获取未压缩公钥 (65字节)
			byte[] pubKey = currentKey.getPubKeyPoint().getEncoded(false);
			
			// 计算 Keccak-256 哈希
			Keccak.Digest256 digest = new Keccak.Digest256();
			byte[] hashedPubKey = digest.digest(pubKey);
			
			// 取最后20字节作为地址
			byte[] addressBytes = new byte[20];
			System.arraycopy(hashedPubKey, hashedPubKey.length - 20, addressBytes, 0, 20);
			
			// 转换为十六进制并添加0x前缀
			String ethAddress = "0x" + bytesToHex(addressBytes);

			// 输出结果
			System.out.println("助记词: " + String.join(" ", mnemonic));
			System.out.println("BIP-44 Path: m/44'/60'/0'/0/0");
			System.out.println("ETH Address: " + ethAddress);
			System.out.println("Public Key: " + pubKey);
			System.out.println("Private Key: " + currentKey.getPrivKey().toString(16));
			
//        deterministicHierarchy.deriveChild(null, true, true, HDUtils.parsePath(path));
//        DeterministicKey deterministicKey = deterministicHierarchy.derive(
//                HDUtils.parsePath(path),
//                true, // 绝对路径
//                true // 是否公开派生
//        );

//         5. 生成以太坊地址
			ECKeyPair ecKeyPair = ECKeyPair.create(currentKey.getPrivKeyBytes());
			String ethAddress1 = Keys.getAddress(ecKeyPair);
			System.out.println("以太坊地址: 0x" + ethAddress1);
			System.out.println("Private Key: " + currentKey.getPrivKey().toString(16));
			System.out.println("End");
//        // 6. 如果需要其他链（如比特币、OKT Chain），调整路径并派生
//        String bitcoinPath = "M/44H/0H/0H/0/0";
//        DeterministicKey bitcoinKey = deterministicHierarchy.derive(
//                HDUtils.parsePath(bitcoinPath),
//                true,
//                true
//        );
			// 比特币地址生成逻辑（需使用 bitcoinj 库）
			// String bitcoinAddress = deriveBitcoinAddress(bitcoinKey);
		} catch (MnemonicLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HDDerivationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void xCreateAccountAddress()  {
		// 生成随机私钥（32字节）
        byte[] privateKeyBytes = generateRandomPrivateKey();
        
        // 生成各链地址
        System.out.println("BTC Address: " + generateBTCAddress(privateKeyBytes));
        System.out.println("ETH Address: " + generateETHAddress(privateKeyBytes));
        System.out.println("BSC Address: " + generateBSCAddress(privateKeyBytes));
        System.out.println("TRON Address: " + generateTRXAddress(privateKeyBytes));
        System.out.println("USDT-ERC20 Address: " + generateETHAddress(privateKeyBytes)); // 与ETH相同
        System.out.println("SOL Address: " + generateSOLAddress());
        System.out.println("xCreateAccountAddress END: ");
        
	}
	
	// 字节数组转十六进制工具方法
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
	
//	public static void exportKeystore(ECKeyPair keyPair, String password) throws CipherException, IOException {
//        WalletFile walletFile = Wallet.createStandard(password, keyPair);
//        String json = WalletUtils.serializeToJson(walletFile);
//        System.out.println("Keystore JSON:\n" + json);
//    }
//
//    public static void main(String[] args) throws Exception {
//        ECKeyPair keyPair = ECKeyPair.create(Numeric.hexStringToByteArray("私钥十六进制"));
//        exportKeystore(keyPair, "your-password");
//    }

	public static String generateSignature(String timestamp, 
            String method, 
            String requestPath, 
            String body, 
            String secretKey) {
		try {
		// 1. 拼接预哈希字符串
		String preHash = timestamp
		+ method.toUpperCase() 
		+ requestPath 
		+ (body != null ? body : "");
		
		// 2. HMAC SHA256加密
		Mac hmac = Mac.getInstance("HmacSHA256");
		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		hmac.init(keySpec);
		byte[] hashBytes = hmac.doFinal(preHash.getBytes(StandardCharsets.UTF_8));
		
		// 3. Base64编码
		return java.util.Base64.getEncoder().encodeToString(hashBytes);
		} catch (Exception e) {
		throw new RuntimeException("签名生成失败", e);
		}
	}
	
	private static final SecureRandom secureRandom = new SecureRandom();
	// 生成随机私钥（32字节）
    private static byte[] generateRandomPrivateKey() {
        byte[] privKey = new byte[32];
        secureRandom.nextBytes(privKey);
        return privKey;
    }
	
	// ================= BTC 地址生成 =================
    public static String generateBTCAddress(byte[] privateKeyBytes) {
        NetworkParameters params = MainNetParams.get();
        ECKey ecKey = ECKey.fromPrivate(privateKeyBytes);
        return Address.fromKey(params, ecKey, ScriptType.P2PKH).toString();
    }

    // ================= ETH/BSC/ERC20 地址生成 =================
    public static String generateETHAddress(byte[] privateKeyBytes) {
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKeyBytes);
        return "0x" + Keys.getAddress(ecKeyPair);
    }

    public static String generateBSCAddress(byte[] privateKeyBytes) {
        // BSC地址与ETH格式相同
        return generateETHAddress(privateKeyBytes);
    }

    // ================= SOL 地址生成 =================
    public static String generateSOLAddress() {
        // 生成ED25519密钥对
        Ed25519KeyPairGenerator gen = new Ed25519KeyPairGenerator();
        gen.init(new Ed25519KeyGenerationParameters(secureRandom));
        
        byte[] publicKey = ((Ed25519PublicKeyParameters)gen.generateKeyPair().getPublic()).getEncoded();
        return Base58.encode(publicKey);
    }
    
    // 添加TRON支持示例
    public static String generateTRXAddress(byte[] privateKeyBytes) {
        byte[] publicKey = ECKey.fromPrivate(privateKeyBytes).getPubKey();
        byte[] hash = Hash.sha3(publicKey);
        return "T" + Base58.encode(Arrays.copyOfRange(hash, 11, 31));
    }

}
