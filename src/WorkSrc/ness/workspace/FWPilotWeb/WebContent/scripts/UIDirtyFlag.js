function Tree(rootId,rootObj)
{
	this.treeText = "";
	this.root = new TreeNode(rootId);
	this.root.setObject(rootObj);
	this.selectedNode = null;
	this.selectedNodeChildren = new Array();
	
	this.addNode = addNode;
	this.addNodeByParent = addNodeByParent;
	this.getNode = getNode;
	this.printNode = printNode;
	this.searchNode = searchNode;
	this.print = print;
	this.search = search;
	this.searchChilds = searchChilds;
	this.searchNodeChilds = searchNodeChilds;
	this.searchDirectNodeChilds = searchDirectNodeChilds;	
	
	function addNodeByParent(parentId,nodeId,obj)
	{
		var parentNode = this.search(parentId);
		if (parentNode != null)
		{
			var newNode = new TreeNode(nodeId);
			newNode.setObject(obj);
			parentNode.addChild(newNode);			
		}
	}
	
	function addNode(path,nodeId,obj)
	{
		var currentNode = this.root;
		var newNode = new TreeNode(nodeId);
		newNode.setObject(obj);
		if (path != null)
		{
			var keys = path.split("|");
			for (index = 0;index < keys.length;index++)
			{
				var key = keys[index];
				if (currentNode.nodeList.get(key) != null)
				{
					currentNode = currentNode.getChild(key);
				}
			}
			currentNode.addChild(newNode);
		}
		else
		{
			this.root.addChild(newNode);
		}
	}
	
	function getNode(path,nodeKey)
	{
		var currentNode = this.root;
		var keys = path.split("|");
		for (index = 0;index < keys.length;index++)
		{
			var key = keys[index];
			currentNode = currentNode.getChild(key);			
		}	
		return currentNode.getChild(nodeKey);	
	}
	
	function print()
	{
		this.printNode(this.root,0);
		alert(this.treeText);
		this.treeText = "";
	}
	
	function printNode(currentNode,level)
	{
		for (i = 0;i < level;i++)
		{
			this.treeText += "   ";
		}
		this.treeText += "node " + currentNode.nodeId + " with obj " + currentNode.obj.toString() + "\n";
		var keys = currentNode.nodeList.getKeys();	
		for (var index = 0,len = keys.length;index < len; index++)
		{	
			this.printNode(currentNode.getChild(keys[index]),level + 1);			
		}
	}
	
	function searchChilds(nodeKey)
	{
		var node = this.search(nodeKey);
		if (node == null) return null;
		this.searchNodeChilds(node);
		var childrenArray = this.selectedNodeChildren; 
		this.selectedNodeChildren = new Array();
		return childrenArray;
	}
	
	function searchNodeChilds(currentNode)
	{
		var keys = currentNode.nodeList.getKeys();	
		for (var index = 0,len = keys.length;index < len; index++)
		{
			var child = currentNode.getChild(keys[index]);
			this.selectedNodeChildren[this.selectedNodeChildren.length] = child.obj;
			this.searchNodeChilds(currentNode.getChild(keys[index]));
		}		
	}
		
	function searchDirectNodeChilds(nodeKey)
	{
		var node = this.search(nodeKey);
		if (node == null) return null;
		return node.nodeList.getValues();	
	}
		
	function search(nodeKey)
	{
		this.searchNode(this.root,nodeKey);
		var node = this.selectedNode;
		this.selectedNode = null;
		return node;
	}

	function searchNode(currentNode,nodeKey)
	{
		if (currentNode.nodeId == nodeKey)
		{
			this.selectedNode = currentNode;
		}
		else
		{
			var keys = currentNode.nodeList.getKeys();
			for (var index = 0,len = keys.length;index < len; index++)
			{	
				if (currentNode.getChild(keys[index]) != null)
					this.searchNode(currentNode.getChild(keys[index]),nodeKey);	
			}		
		}
	}

	function TreeNode(nodeId)
	{
		this.nodeId = nodeId;
		this.nodeList = new HashMap();	
		this.setObject = setObject;
		this.addChild = addChild;
		this.getChild = getChild;
		this.getObject = getObject; 
		this.hasChildren = hasChildren;
		
		function setObject(obj)
		{
			this.obj = obj;
		}
		
		function getObject(obj)
		{
			return this.obj;
		}		
		
		function addChild(treeNode)
		{
			this.nodeList.put(treeNode.nodeId,treeNode);
		}	
		
		function getChild(nodeId)
		{
			return this.nodeList.get(nodeId);
		}	
		
		function hasChildren()
		{
			return this.nodeList.size() > 0;
		}	
	}
}


function HashMap()
{
	this.hash = new Array();
	this.list = new Array();
	this.put = put;
	this.get = get;
	this.size = size;
	this.getKeys = getKeys;
	this.getValues = getValues;
	this.print = print;
	
	function put(id,obj)
	{
		var isExist = (this.hash[id] != null);
		this.hash[id] = obj;
		if (!isExist)
		{
			this.list[this.list.length] = id;
		}
	}
	
	function get(id)
	{
		return this.hash[id];
	}
	
	function size()
	{
		return this.list.length;
	}
	
	function getKeys()
	{
		return this.list;
	}
	
	function getValues()
	{
		var values = new Array();
		for (index = 0;index < this.list.length;index++)
		{
			values[values.length] = this.get(this.list[index]);
		}
		return values;
	}
	
	function print()
	{
		var tmp = "";
		for (index = 0;index < this.size();index++)
		{
			tmp += "key:" + this.list[index] + " object:" + this.hash[this.list[index]];
		}
		alert(tmp);
	}
}

function DirtyFlag(dirtyFlagId,message)
{
	this.dirtyFlagId = dirtyFlagId;
	this.message = message;
	this.toString = toString;
	function toString()
	{
		//return "message of " + dirtyFlagId + " is " + message;
		return dirtyFlagId;
	}
}

/**
 * Adds DirtyFlag object to the dirtyTree	
 */
function addDirtyFlag(path,dirtyFlag)
{
	if (dirtyTree != null)
	{
		dirtyTree.addNode(path,dirtyFlag.dirtyFlagId,dirtyFlag);
	}
	else
	{
		"DirtyTree was not initialized";
	}
}

/**
 * Returns the id of the global dirty flag	
 */
function getGlobalDirtyFlag()
{
	if (dirtyTree != null)
	{
		return dirtyTree.root.obj;
	}
	else
	{
		"DirtyTree was not initialized";
	}
}

/**
 * Marks a dirty flag as dirty 
 */
function setDirty(dirtyModelId)
{
	if (window.event.propertyName != "style.visibility")
	{	
		if (dirtyModelId == null)
		{
			setGlobalDirty(); 
		}
		else
		{
			var form = document.forms[0];
			hiddenField = form[dirtyModelId];
			if (hiddenField != null)
			{
				setModelProperty(hiddenField,DIRTY_MODEL_ISDIRTY_PROPERTY,DIRTY_MODEL_DIRTY);
			}
		}
	}
}

/**
 * Marks the global dirty flag as dirty 
 */
function setGlobalDirty()
{
	var form = document.forms[0];
	var hiddenField = form[DIRTY_MODEL_ID];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,DIRTY_MODEL_ISDIRTY_PROPERTY,DIRTY_MODEL_DIRTY);
}
 
/**
 * Returns true if a dirty flag is marked as dirty
 */
function isDirty(dirtyModelId)
{
	var form = document.forms[0];
	var dirtyField = form[dirtyModelId];
	if (dirtyField == null) 
	{	
		return false;
	}
	else
	{
		return dirtyField.value.indexOf(DIRTY_MODEL_DIRTY) != -1
	}
} 