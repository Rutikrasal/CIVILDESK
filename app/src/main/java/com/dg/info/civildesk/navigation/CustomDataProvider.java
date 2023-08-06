package com.dg.info.civildesk.navigation;


import android.content.Context;


import com.dg.info.civildesk.R;
import com.dg.info.civildesk.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomDataProvider extends BaseActivity {

    private static final int MAX_LEVELS = 3;

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static List<BaseItem> mMenu = new ArrayList<>();
    Context context;

    public static List<BaseItem> getInitialItems() {
        //return getSubItems(new GroupItem("root"));

        List<BaseItem> rootMenu = new ArrayList<>();

        rootMenu.add(new Item("Dashboard", R.drawable.icon_category));
        rootMenu.add(new Item("Sites", R.drawable.ic_work));
        rootMenu.add(new Item("Items", R.drawable.ic_items));
        rootMenu.add(new Item("Vendors", R.drawable.ic_customer));
        rootMenu.add(new Item("Purchase Bills", R.drawable.ic_cart));
        rootMenu.add(new Item("Payment Out", R.drawable.ic_paymentout));
        rootMenu.add(new Item("Purchase Return", R.drawable.ic_purchasereturn));
        rootMenu.add(new Item("Purchase Order", R.drawable.ic_paymentin));
        rootMenu.add(new Item("Expense", R.drawable.ic_expense));
        rootMenu.add(new Item("Logout", R.drawable.ic_logout));

        return rootMenu;
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {

        List<BaseItem> result = new ArrayList<>();
        int level = ((GroupItem) baseItem).getLevel() + 1;
        String menuItem = baseItem.getName();
        if (!(baseItem instanceof GroupItem)) {
            throw new IllegalArgumentException("GroupItem required");
        }

        GroupItem groupItem = (GroupItem) baseItem;
        if (groupItem.getLevel() >= MAX_LEVELS) {
            return null;
        }

        /**
        * ONLY FOR GROUP-ITEM
        **/
        switch (level) {
            case LEVEL_1:
                switch (menuItem.toUpperCase()) {

                    case "MY PROFILE":
                        result = getListMyProfile();
                        break;
                    case "MANAGE":
                        result = getListManage();
                        break;

                }
                break;
        }
        return result;
    }

    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }

    private static List<BaseItem> getListMyProfile() {
        List<BaseItem> list = new ArrayList<>();
/*        list.add(new Item("Update Profile Image", R.drawable.ic_update_profile));
        list.add(new Item("Change Password",R.drawable.ic_done));
        list.add(new Item("Update Email ID",R.drawable.ic_done));*/


        return list;
    }

    private static List<BaseItem> getListManage() {
        List<BaseItem> list = new ArrayList<>();
/*        list.add(new Item("My Team",R.drawable.ic_retailes));
        list.add(new Item("Leads",R.drawable.ic_leads));
        list.add(new Item("Leave Approval",R.drawable.ic_leave_approve));
        list.add(new Item("Team Target",R.drawable.ic_goal));*/
        return list;
    }


}
