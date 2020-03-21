package BaseDeDonne;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import Modele.BlockInventory;
import Modele.EG_Loccation;
import Modele.PorteAClef;
import Utils.EG_Exception;
import Utils.Log;
import fr.xam74er1.spigot.Main;
import net.minecraft.server.v1_14_R1.PlayerInventory;

public class DAO_BlockInventory extends DAO<BlockInventory>{

	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS blockInventory ("
			+ "	id integer PRIMARY KEY,"
			+ "	locID integer UNIQUE,"
			+ "	contenue TEXT,"
			+" type integer,"
			+"FOREIGN KEY (locID) REFERENCES Location(id)"
			+ ");";


	static String tableName = "blockInventory";



	@Override
	public int insert(BlockInventory obj) throws Exception {
		// TODO Auto-generated method stub

		Log.print("inser binv");

		Inventory inv = obj.getInv();

		String b64 = toBase64(inv);

		int max = getMaxID(tableName);


		return exeUpdate("INSERT INTO "+tableName+" VALUES ("+(max+1)+","+obj.getLocation().getId()+",\""+b64+"\","+obj.getType()+");"
				);
	}

	@Override
	public boolean delete(BlockInventory obj) {
		int  rs;
		try {
			rs = exeUpdate("DELETE FROM "+tableName+" WHERE id = "+obj.getId() );
			if(rs==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean delete(EG_Loccation obj) {
		//Nous devons enelve dans les sous table si il existe 

		BlockInventory binv = find(obj.getId());

		//Si il existe j'enleve dans les sous table 
		if(binv!=null) {
			Main.getGame().removeBlockInventory(binv);
			new DAO_BlockInventoryCode().delete(binv);
			new DAO_BlockInventoryKey().delete(binv);
		}else {
			//Si il ny a pas de binv avec cette id je retoune faux 
			return false;
		}

		int rs;
		try {
			rs = exeUpdate("DELETE FROM "+tableName+" WHERE LocID = "+obj.getId() );
			if(rs==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public int update(BlockInventory obj) {
		// TODO Auto-generated method stub

		try {


			Inventory inv = obj.getInv();

			String b64 = toBase64(inv);
			int nb = exeUpdate("UPDATE "+tableName+" SET contenue= \""+b64+"\",type = "+obj.getType()+""
					+ " WHERE id = "+obj.getId());

			Log.print(nb+"Ligne mise a jour");

			return nb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;


	}

	@Override
	public BlockInventory find(int id) {

		// TODO Auto-generated method stub



		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE locID = "+id );

			if(rs.isClosed())
				return null;
			Inventory inv ;

			try {
				try {
					String b64 = rs.getString("contenue");
					inv = fromBase64(b64);
				}catch(java.lang.IllegalArgumentException e3) {
					inv = null;
					e3.printStackTrace();
				}
				BlockInventory binv = new BlockInventory(rs.getInt("id"), inv,rs.getInt("type"), id);
				rs.close();
				return binv;
			} catch (EG_Exception | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;


	}


	/**
	 * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is the armor.
	 * 
	 * @param playerInventory to turn into an array of strings.
	 * @return Array of strings: [ main content, armor content ]
	 * @throws IllegalStateException
	 */
	//    public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
	//    	//get the main content part, this doesn't return the armor
	//    	String content = toBase64(playerInventory);
	//    	String armor = itemStackArrayToBase64(playerInventory.getArmorContents());
	//    	
	//    	return new String[] { content, armor };
	//    }

	/**
	 * 
	 * A method to serialize an {@link ItemStack} array to Base64 String.
	 * 
	 * <p />
	 * 
	 * Based off of {@link #toBase64(Inventory)}.
	 * 
	 * @param items to turn into a Base64 String.
	 * @return Base64 string of the items.
	 * @throws IllegalStateException
	 */
	public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(items.length);

			// Save every element in the list
			for (int i = 0; i < items.length; i++) {
				dataOutput.writeObject(items[i]);
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	/**
	 * A method to serialize an inventory to Base64 string.
	 * 
	 * <p />
	 * 
	 * Special thanks to Comphenix in the Bukkit forums or also known
	 * as aadnk on GitHub.
	 * 
	 * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
	 * 
	 * @param inventory to serialize
	 * @return Base64 string of the provided inventory
	 * @throws IllegalStateException
	 */
	public static String toBase64(Inventory inventory) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(inventory.getSize());

			// Save every element in the list
			for (int i = 0; i < inventory.getSize(); i++) {
				dataOutput.writeObject(inventory.getItem(i));
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	/**
	 * 
	 * A method to get an {@link Inventory} from an encoded, Base64, string.
	 * 
	 * <p />
	 * 
	 * Special thanks to Comphenix in the Bukkit forums or also known
	 * as aadnk on GitHub.
	 * 
	 * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
	 * 
	 * @param data Base64 string of data containing an inventory.
	 * @return Inventory created from the Base64 string.
	 * @throws IOException
	 */
	public static Inventory fromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

			// Read the serialized inventory
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}

			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	/**
	 * Gets an array of ItemStacks from Base64 string.
	 * 
	 * <p />
	 * 
	 * Base off of {@link #fromBase64(String)}.
	 * 
	 * @param data Base64 string to convert to ItemStack array.
	 * @return ItemStack array created from the Base64 string.
	 * @throws IOException
	 */
	public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack[] items = new ItemStack[dataInput.readInt()];

			// Read the serialized inventory
			for (int i = 0; i < items.length; i++) {
				items[i] = (ItemStack) dataInput.readObject();
			}

			dataInput.close();
			return items;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static String getTableName() {
		return tableName;
	}

	public static void setTableName(String tableName) {
		DAO_BlockInventory.tableName = tableName;
	}



}
