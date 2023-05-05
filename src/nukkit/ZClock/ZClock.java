package nukkit.ZClock;

import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.yescallop.essentialsnk.EssentialsAPI;

public class ZClock extends PluginBase implements Listener {

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info(TextFormat.LIGHT_PURPLE + "賢者懷錶插件 [ZeroK製作]");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Item clock = new Item(347, 0, 1);
		clock.setCustomName("§r§e賢者懷錶");
		clock.setLore("傳說中賢者尻尻所持有的神器。");
		if (!player.getInventory().contains(clock)) {
			player.getInventory().setItem(8, clock);
			return;
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Item item = event.getItem();
		if (item.getId() == 347 && item.getCustomName().equals("§r§e賢者懷錶")) {
			this.sendMenu(player);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Item item = event.getItem();
		if (event.isCancelled()) {
			return;
		}
		if (item.getId() == 347 && item.getCustomName().equals("§r§e賢者懷錶")) {
			event.setCancelled();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Item item = event.getSourceItem();
		if (event.isCancelled()) {
			return;
		}

		if (item.getId() == 347 && item.getCustomName().equals("§r§e賢者懷錶")) {
			event.setCancelled();
		}
	}

	@EventHandler
	public void formRespond(PlayerFormRespondedEvent event) {
		Player player = event.getPlayer();
		FormWindow window = event.getWindow();
		if (window instanceof FormWindowSimple) {
			String title = ((FormWindowSimple) event.getWindow()).getTitle();
			if (title.equals("賢者懷錶")) {
				if (event.wasClosed()) {
					return;
				}
				String button = ((FormResponseSimple) event.getResponse()).getClickedButton().getText();
				if (button.equals("傳送系統")) {
					this.sendTeleportList(player);
					return;
				}
				if (button.equals("經濟系統")) {
					this.sendEconomyList(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統")) {
				if (event.wasClosed()) {
					this.sendMenu(player);
					return;
				}
				String button = ((FormResponseSimple) event.getResponse()).getClickedButton().getText();
				if (button.equals("傳送至重生點")) {
					getServer().getInstance().dispatchCommand(player, "spawn");
					return;
				}
				if (button.equals("傳送自己至他人的位置")) {
					this.sendTeleportToOtherPlayer(player);
					return;
				}
				if (button.equals("傳送他人至我的位置")) {
					this.sendTeleportPlayerToMe(player);
					return;
				}
				if (button.equals("傳送至傳送點")) {
					this.sendTeleportHome(player);
					return;
				}
				if (button.equals("設置傳送點")) {
					this.sendTeleportsetHome(player);
					return;
				}
				if (button.equals("刪除傳送點")) {
					this.sendTeleportdelHome(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 經濟系統")) {
				if (event.wasClosed()) {
					this.sendMenu(player);
					return;
				}
				String button = ((FormResponseSimple) event.getResponse()).getClickedButton().getText();
				if (button.equals("付款")) {
					this.sendEconomyPay(player);
					return;
				}
				if (button.equals("查看自己錢包")) {
					getServer().getInstance().dispatchCommand(player, "mymoney");
					return;
				}
				if (button.equals("查看他人錢包")) {
					this.sendEconomySeeMoney(player);
					return;
				}
				if (button.equals("查看富豪榜")) {
					getServer().getInstance().dispatchCommand(player, "topmoney");
					player.sendMessage(TextFormat.DARK_GRAY + "[" + TextFormat.YELLOW + "!" + TextFormat.DARK_GRAY
							+ "] " + TextFormat.WHITE + "以下為富豪榜第一頁的內容，請使用 " + TextFormat.YELLOW + "/topmoney <頁數> "
							+ TextFormat.WHITE + "查看指定頁數");
					return;
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (tpa)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (tpahere)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (home)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (delhome)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 經濟系統 (pay)")) {
				if (event.wasClosed()) {
					this.sendEconomyList(player);
					return;
				}
			}
			if (title.equals("賢者懷錶 > 經濟系統 (seemoney)")) {
				if (event.wasClosed()) {
					this.sendEconomyList(player);
					return;
				}
			}
		}
		if (window instanceof FormWindowCustom) {
			FormResponseCustom result = (FormResponseCustom) event.getResponse();
			String title = ((FormWindowCustom) window).getTitle();
			if (title.equals("賢者懷錶 > 傳送系統 (tpa)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
				FormResponseData dropDownData = result.getDropdownResponse(0);
				String dropDown = dropDownData.getElementContent();
				if (dropDown != null) {
					getServer().getInstance().dispatchCommand(player, "tpa " + dropDown);
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (tpahere)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
				FormResponseData dropDownData = result.getDropdownResponse(0);
				String dropDown = dropDownData.getElementContent();
				if (dropDown != null) {
					getServer().getInstance().dispatchCommand(player, "tpahere " + dropDown);
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (home)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
				FormResponseData dropDownData = result.getDropdownResponse(0);
				String dropDown = dropDownData.getElementContent();
				if (dropDown != null) {
					getServer().getInstance().dispatchCommand(player, "home " + dropDown);
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (sethome)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
				String Input = result.getInputResponse(0);
				if (Input != null) {
					getServer().getInstance().dispatchCommand(player, "sethome " + Input);
				}
			}
			if (title.equals("賢者懷錶 > 傳送系統 (delhome)")) {
				if (event.wasClosed()) {
					this.sendTeleportList(player);
					return;
				}
				FormResponseData dropDownData = result.getDropdownResponse(0);
				String dropDown = dropDownData.getElementContent();
				if (dropDown != null) {
					getServer().getInstance().dispatchCommand(player, "delhome " + dropDown);
				}
			}
			if (title.equals("賢者懷錶 > 經濟系統 (pay)")) {
				if (event.wasClosed()) {
					this.sendEconomyList(player);
					return;
				}
				FormResponseData dropDownData = result.getDropdownResponse(0);
				String dropDown = dropDownData.getElementContent();
				String Input = result.getInputResponse(1);
				if (Input != null && dropDown != null) {
					getServer().getInstance().dispatchCommand(player, "pay " + dropDown + " " + Input);
				}
			}
			if (title.equals("賢者懷錶 > 經濟系統 (seemoney)")) {
				if (event.wasClosed()) {
					this.sendEconomyList(player);
					return;
				}
				FormResponseData dropDownData = result.getDropdownResponse(0);
				String dropDown = dropDownData.getElementContent();
				if (dropDown != null) {
					getServer().getInstance().dispatchCommand(player, "seemoney " + dropDown);
				}
			}
		}
	}

	public void sendMenu(Player player) {
		FormWindowSimple window = new FormWindowSimple("賢者懷錶", "請選擇您需要的幫助:");
		window.addButton(new ElementButton("傳送系統"));
		window.addButton(new ElementButton("經濟系統"));
		player.showFormWindow(window);
	}

	// Teleport Part Start
	public void sendTeleportList(Player player) {
		FormWindowSimple window = new FormWindowSimple("賢者懷錶 > 傳送系統", "請選擇您要進行的動作:");
		window.addButton(new ElementButton("傳送至重生點"));
		window.addButton(new ElementButton("傳送自己至他人的位置"));
		window.addButton(new ElementButton("傳送他人至我的位置"));
		window.addButton(new ElementButton("傳送至傳送點"));
		window.addButton(new ElementButton("設置傳送點"));
		window.addButton(new ElementButton("刪除傳送點"));
		player.showFormWindow(window);
	}

	public void sendTeleportToOtherPlayer(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 傳送系統 (tpa)");
		ElementDropdown chooseplayer = new ElementDropdown("選擇您要進行傳送的玩家:");
		for (Player pl : this.getServer().getOnlinePlayers().values()) {
			String playername = pl.getName();
			if (!playername.equals(player.getName())) {
				chooseplayer.addOption(playername);
			}
		}
		int onlineCount = 0;
		for (Player p : this.getServer().getOnlinePlayers().values()) {
			if (p.isOnline() && (!(p instanceof Player))) {
				++onlineCount;
			}
		}
		if (onlineCount > 1) {
			window.addElement(chooseplayer);
			player.showFormWindow(window);
		} else {
			FormWindowSimple notthing = new FormWindowSimple("賢者懷錶 > 傳送系統 (tpa)", "很抱歉，目前線上沒有可供您傳送的玩家。");
			player.showFormWindow(notthing);
		}
	}

	public void sendTeleportPlayerToMe(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 傳送系統 (tpahere)");
		ElementDropdown chooseplayer = new ElementDropdown("選擇您要進行傳送的玩家:");
		for (Player pl : this.getServer().getOnlinePlayers().values()) {
			String playername = pl.getName();
			if (!playername.equals(player.getName())) {
				chooseplayer.addOption(playername);
			}
		}
		int onlineCount = 0;
		for (Player p : this.getServer().getOnlinePlayers().values()) {
			if (p.isOnline() && (!(p instanceof Player))) {
				++onlineCount;
			}
		}
		if (onlineCount > 1) {
			window.addElement(chooseplayer);
			player.showFormWindow(window);
		} else {
			FormWindowSimple notthing = new FormWindowSimple("賢者懷錶 > 傳送系統 (tpahere)", "很抱歉，目前線上沒有可供您傳送的玩家。");
			player.showFormWindow(notthing);
		}
	}

	public void sendTeleportHome(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 傳送系統 (home)");
		ElementDropdown choosehome = new ElementDropdown("選擇您要進行傳送的傳送點:");
		int hc = 0;
		for (String homes : EssentialsAPI.getInstance().getHomesList(player)) {
			choosehome.addOption(homes);
			++hc;
		}
		if (hc > 0) {
			window.addElement(choosehome);
			player.showFormWindow(window);
		} else {
			FormWindowSimple notthing = new FormWindowSimple("賢者懷錶 > 傳送系統 (home)", "很抱歉，您目前沒有設置任何傳送點。");
			player.showFormWindow(notthing);
		}
	}

	public void sendTeleportsetHome(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 傳送系統 (sethome)");
		window.addElement(new ElementInput("請填入您想要的傳送點名稱:"));
		player.showFormWindow(window);
	}

	public void sendTeleportdelHome(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 傳送系統 (delhome)");
		ElementDropdown choosehome = new ElementDropdown("選擇您要刪除的傳送點:");
		int hc = 0;
		for (String homes : EssentialsAPI.getInstance().getHomesList(player)) {
			choosehome.addOption(homes);
			++hc;
		}
		if (hc > 0) {
			window.addElement(choosehome);
			player.showFormWindow(window);
		} else {
			FormWindowSimple notthing = new FormWindowSimple("賢者懷錶 > 傳送系統 (delhome)", "很抱歉，您目前沒有設置任何傳送點。");
			player.showFormWindow(notthing);
		}
	}
	// Teleport Part End

	// Economy Part Start
	public void sendEconomyList(Player player) {
		FormWindowSimple window = new FormWindowSimple("賢者懷錶 > 經濟系統", "請選擇您要進行的動作:");
		window.addButton(new ElementButton("付款"));
		window.addButton(new ElementButton("查看自己錢包"));
		window.addButton(new ElementButton("查看他人錢包"));
		window.addButton(new ElementButton("查看富豪榜"));
		player.showFormWindow(window);
	}

	public void sendEconomyPay(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 經濟系統 (pay)");
		ElementDropdown chooseplayer = new ElementDropdown("※ 如付款對象為離線玩家，請使用 /pay <玩家ID> 進行操作。\n\n選擇您要進行付款的玩家:");
		for (Player pl : this.getServer().getOnlinePlayers().values()) {
			String playername = pl.getName();
			if (!playername.equals(player.getName())) {
				chooseplayer.addOption(playername);
			}
		}
		int onlineCount = 0;
		for (Player p : this.getServer().getOnlinePlayers().values()) {
			if (p.isOnline() && (!(p instanceof Player))) {
				++onlineCount;
			}
		}
		if (onlineCount > 1) {
			window.addElement(chooseplayer);
			window.addElement(new ElementInput("請填入您要付款的金額:"));
			player.showFormWindow(window);
		} else {
			FormWindowSimple notthing = new FormWindowSimple("賢者懷錶 > 經濟系統 (seemoney)",
					"※ 如查看對象為離線玩家，請使用 /pay <玩家ID> <金額>  進行操作。\n\n很抱歉，目前線上沒有可供您付款的玩家。");
			player.showFormWindow(notthing);
		}
	}

	public void sendEconomySeeMoney(Player player) {
		FormWindowCustom window = new FormWindowCustom("賢者懷錶 > 經濟系統 (seemoney)");
		ElementDropdown chooseplayer = new ElementDropdown("※ 如查看對象為離線玩家，請使用 /seemoney <玩家ID> 進行操作。\n\n選擇您要進行查看的玩家:");
		for (Player pl : this.getServer().getOnlinePlayers().values()) {
			String playername = pl.getName();
			if (!playername.equals(player.getName())) {
				chooseplayer.addOption(playername);
			}
		}
		int onlineCount = 0;
		for (Player p : this.getServer().getOnlinePlayers().values()) {
			if (p.isOnline() && (!(p instanceof Player))) {
				++onlineCount;
			}
		}
		if (onlineCount > 1) {
			window.addElement(chooseplayer);
			player.showFormWindow(window);
		} else {
			FormWindowSimple notthing = new FormWindowSimple("賢者懷錶 > 經濟系統 (seemoney)",
					"※ 如查看對象為離線玩家，請使用 /seemoney <玩家ID> 進行操作。\n\n很抱歉，目前線上沒有可供您查看的玩家。");
			player.showFormWindow(notthing);
		}
	}
	// Economy Part End
}
