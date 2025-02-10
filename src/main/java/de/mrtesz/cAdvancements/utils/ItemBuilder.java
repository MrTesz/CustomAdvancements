package de.mrtesz.cAdvancements.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(Material mat){
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            System.out.println("ItemMeta konnte nicht für das Material " + mat + " erstellt werden.");
        }
    }

    public ItemBuilder setDisplayname(String s) {
        itemMeta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setCustomModelData(int i) {
        itemMeta.setCustomModelData(i);
        return this;
    }

    public ItemBuilder addEnchantments(Enchantment e, int i, boolean b) {
        itemMeta.addEnchant(e, i, b);
        return this;
    }

    public ItemBuilder setLore(String... s){
        itemMeta.setLore(Arrays.asList(s));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean s){
        itemMeta.setUnbreakable(s);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... s){
        itemMeta.addItemFlags(s);
        return this;
    }

    public ItemBuilder setBookPages(String... pages) {
        if (itemMeta instanceof BookMeta bookMeta) {
            bookMeta.setPages(pages);
            itemMeta = bookMeta;
        }
        return this;
    }

    public ItemBuilder setAuthor(String author) {
        if (itemMeta instanceof BookMeta bookMeta) {
            bookMeta.setAuthor(author);
            itemMeta = bookMeta;
        }
        return this;
    }

    public ItemBuilder addPage(String... page) {
        if(itemMeta instanceof BookMeta bookMeta) {
            bookMeta.addPage(page);
            itemMeta = bookMeta;
        }
        return this;
    }

    public ItemBuilder setTitle(String title) {
        if (itemMeta instanceof BookMeta bookMeta) {
            bookMeta.setTitle(title);
            itemMeta = bookMeta;
        }
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }

    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

