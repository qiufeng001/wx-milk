//生成左侧Menu菜单
function initMenu(contextPath, obj) {
	var treeHtml = "";
	$(obj)
			.each(
					function(index) {
						// 菜单头HTML
						var tempHead = "<li><a data-menuName="
								+ obj[index].name
								+ " data-menuId="
								+ obj[index].id
								+ " href="
								+ obj[index].url
								+ " class='dropdown-toggle'><span class='menu-text'>"
								+ obj[index].name
								+ "</span><b class='arrow icon-angle-down'></b></a><ul class='submenu'>";
						// 菜单体HTML
						var tempBody = "<li ><a data-menuName="
								+ obj[index].name + " data-menuId="
								+ obj[index].id + " href=" + obj[index].url
								+ "><span class='menu-text'> "
								+ obj[index].name + " </span></a></li>";
						// 菜单尾HTML
						var tempFoot = "</ul></li>";
						// 当前节点菜单等级 eq:001001 2级菜单
						var level = obj[index].id.length / 3;
						// 当前节点所对应下一节点数组
						var attrLevel = getNextLevel(obj);
						// 当前节点所对应下一节点菜单等级
						var attrUrl = getNextUrl(obj);
						var pLevel = attrLevel[index + 1].length / 3;
						var pUrl = attrUrl[index + 1];
						// url为"#",有下级菜单，拼出菜单头部
						if (obj[index].url == '#' && level < pLevel) {
							treeHtml += tempHead;
						}
						// url不为"#",并且当前节点与下级节点同级，说明下一节点有同级菜单，拼菜单体
						else if (obj[index].url != '#' && level == pLevel) {
							treeHtml += tempBody;
						}
						// url不为"#",并且当前节点大于下级节点,下级节点不为"#"，有level-pLevel-1级子菜单
						else if (pUrl != '#' && level > pLevel
								&& obj[index].url != '#') {
							var tempNewFoot = tempFoot;
							// alert(obj[index].name+" 等级差："+(level-pLevel-1));
							for (var i = 0; i < (level - pLevel - 1); i++) {
								// tempFoot += tempFoot;
								tempNewFoot = tempNewFoot + tempFoot;
								// alert(obj[index].name+"
								// 等级差："+(level-pLevel-1)+" "+tempFoot);
							}
							treeHtml = treeHtml + tempBody + tempNewFoot;
						}
						// url不为"#"，并且当前节点与下级节点不同级，说明下一节点无同级菜单，拼接菜单体、菜单尾*等级
						else if (obj[index].url != '#' && level != pLevel) {
							// var tempNewFoot="";

							for (var i = 1; i < (level - pLevel); i++) {
								tempFoot = tempFoot + tempFoot;
								// tempNewFoot = tempNewFoot + tempFoot;
								// alert(obj[index].name+"
								// 等级差："+(level-pLevel)+" "+tempFoot);
							}
							// alert(obj[index].name+tempFoot);
							treeHtml = treeHtml + tempBody + tempFoot;
						}
						// url不为"#",并且当前节点与下级节点同级，说明下一节点有同级菜单，拼菜单体
						else if (obj[index].url != '#' && level == pLevel
								&& pUrl == '#') {
							treeHtml += tempBody;
						}
					});
	$('#menuTree').append(treeHtml);

}
// 取当前节点的下一结点等级
function getNextLevel(obj) {
	var attr = new Array();
	$(obj).each(function(index) {
		attr.push(obj[index].id);
	});
	attr.push("0");
	return attr;
}
// 取当前节点的下一结点路径
function getNextUrl(obj) {
	var attr = new Array();
	$(obj).each(function(index) {
		attr.push(obj[index].url);
	});
	attr.push("#");
	return attr;
}
