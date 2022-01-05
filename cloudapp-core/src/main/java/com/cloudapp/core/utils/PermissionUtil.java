package com.cloudapp.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.cloudapp.core.entity.Admin;
import com.cloudapp.core.utils.bean.Menu;
import com.cloudapp.core.utils.bean.Path;
import com.cloudapp.core.utils.bean.PathGroup;
import com.cloudapp.core.utils.bean.Permission;
import com.cloudapp.core.utils.bean.PermissionGroup;
import com.cloudapp.lib.MetaUtil;
import com.cloudapp.lib.xml.XmlParser;

public class PermissionUtil {

	private static List<PathGroup> pathGroups;

	private static Map<String, Path> pathMap;

	private static List<PermissionGroup> permissionGroups;
	
	public static Path getPath(String code) {
		return pathMap.get(code);
	}

	public static List<PathGroup> getPathGroups() {
		return pathGroups;
	}

	/**
	 * 用于角色的权限分配
	 * 
	 * @return
	 */

	public static List<PermissionGroup> getPermissionGroups() {
		return permissionGroups;
	}

	public static void initAdminPermission(Admin admin) {
		List<Menu> menus = admin.getMenus();
		List<String> availablePaths = admin.getAvailablePaths();
		for (PathGroup pg : PermissionUtil.getPathGroups()) {
			boolean pga = pg.isVisible();
			if(pga){
				Menu menu = new Menu(pg);
				List<Path> mps = menu.getPaths();
				
				for (Path p : pg.getPaths()) {
					// 验证该用户在数据库中的权限
					String code = p.getCode();
					if((availablePaths!=null && availablePaths.contains(code)) || admin.getUsername().equals("develop")){
						if(p.isVisible()){
							mps.add(p);
						}
					}
				}
				// 防止出现子项均不可见的空菜单
				if (!mps.isEmpty())
					menus.add(menu);
			}
		}
	}

	/**
	 * 权限加载
	 * 
	 * @param permission_config_file_path 权限配置文件路径
	 * 
	 */
	public static void init(String permission_config_file) {
		pathGroups = new ArrayList<PathGroup>();
		pathMap = new HashMap<String, Path>();
		permissionGroups = new ArrayList<PermissionGroup>();

		Document doc = XmlParser.getDoc(MetaUtil
				.getMetaDataInputStream(permission_config_file));
		// 遍历路径节点
		List<Element> pges = XmlParser.getRootElements(doc, "pathGroup");
		for (Element pge : pges) {
			PathGroup pg = (PathGroup) XmlParser.parseElement(pge,
					PathGroup.class);

			pathGroups.add(pg);
			List<Element> pes = XmlParser.getChildElements(pge, "path");
			for (Element pe : pes) {
				Path p = (Path) XmlParser.parseElement(pe, Path.class);

				pg.addPath(p);
				pathMap.put(p.getCode(), p);
			}
		}

		// 遍历权限节点
		List<Element> pmges = XmlParser.getRootElements(doc, "permissionGroup");
		for (Element pmge : pmges) {
			PermissionGroup pg = (PermissionGroup) XmlParser.parseElement(pmge,
					PermissionGroup.class);

			List<Permission> ps = pg.getPermissions();

			// 可见权限则加入权限列表中
			if (pg.isVisible()) {
				permissionGroups.add(pg);
			}

			List<Element> pes = XmlParser.getChildElements(pmge, "permission");
			for (Element pe : pes) {
				Permission p = (Permission) XmlParser.parseElement(pe,
						Permission.class);

				ps.add(p);

				List<Element> ies = XmlParser.getChildElements(pe, "item");
				for (Element ie : ies)
					p.addItem(XmlParser.parseAttribute(ie, "path"));
			}
		}
	}
}