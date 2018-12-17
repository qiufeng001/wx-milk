<#-- 
id:
title:
iconCls:
action:
type: 1-新增 2-修改 3-删除 4-审单 5-导出       0代表不验证权限的按钮

验证： checkPower("${thisPowerV?number}",4)==true
-->
<#macro toolbar  id  listData  >
<div id="${id}"></div>
<#assign thisPowerV="${thisPower!0}" >
<script type="text/javascript">
  var toolbarList=[];
  <#list listData as item>
      <#assign flag=1>
      <#if (item.type)?? >
          <#if (item.type==1) || (item.type==2) ||(item.type==3)||(item.type==4)||(item.type==5) >
                <#if  checkPower("${thisPowerV?number}",item.type)==false>
                     <#assign flag=0>
                     
                </#if>
          </#if>
       
          var this_bar={};
          this_bar.text='${item.title}';
         
          		this_bar.id='${item.id}';
          		this_bar.iconCls='${item.iconCls}';
          		this_bar.handler=function(){${item.action}};
        
           
           <#--
			如果lsp-config中有配置toolbar.disable=1，则新增，删除按钮无效
			不让其进行新增，修改
			--> 
           
        <#if flag==1>
           toolbarList.push(this_bar);
           toolbarList.push('-');
        </#if>
      </#if> 
  </#list>
  toolbarList.pop();
  $('#${id}').toolbar({items:toolbarList});
</script>
</#macro>
