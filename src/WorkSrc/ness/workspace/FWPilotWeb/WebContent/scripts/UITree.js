/********************functions for handling tree************************/
var openNodeSign = new Array();
var closeNodeSign = new Array();
var selectedNodesArray = new Array();

function openTree(nodeSign,isResize)
{
	var openNodes = new String("");	
	var isRoot = false;
	var nodeId = nodeSign.id;
	var nodePath = nodeSign.path;
	var node = getObjectById(TREE_ROW_PREFIX + nodeId);
	var treeDiv = node.parentElement.parentElement.parentElement;
	var nodeLevel = new Number(node.level);
	var modelId;
	if (nodeId.indexOf(SEPERATOR_MINUS) == -1) 
	{
		modelId = nodeId;
		isRoot = true;
	}
	else 
	{
		modelId = nodeId.substring(0,nodeId.indexOf(SEPERATOR_MINUS));
	}
	var currentLevel;
	if (node.hasSons == "true") 
	{
		switchSigns(nodeSign,modelId);
		var nodes = node.parentElement.parentElement.rows;
		var isOpenAction = (node.open == "false");
		if (isOpenAction)
		{
			node.open = "true";
		}
		else
		{
			node.open = "false";
		}
		var nodesLength = nodes.length;
		var tempNodeArray = new Array();
		var	lastDirectSon;
		for (i = 0;i < nodesLength;i++) 
		{
			var currentNode = nodes[i];
			currentLevel = new Number(currentNode.level);
			//if node is son of the node that was pressed
			if (isRoot && i != 0 || isParent(currentNode.id,node.id) && currentNode.id != node.id) {
				//open action
				if (isOpenAction) 
				{
					tempNodeArray[currentNode.id] = currentNode;
					//direct son of the node that was pressed
					if (currentLevel - 1 == nodeLevel) 
					{						
						currentNode.pOpen = "true";
						show(currentNode);
						lastDirectSon = currentNode;
					} 
					else if (currentNode.pOpen == "true") 
					{
						var parentNode = tempNodeArray[getNodeParentId(currentNode.id)];	
						if (isDisplayed(parentNode)) 
						{
							show(currentNode);
						}						
					}			
				}
				
				/*close action*/			
				else
				{
					hide(currentNode);
					if (currentLevel - 1 == nodeLevel) 
					{
						currentNode.pOpen = "false";
					}
				}						
			}
			if (currentNode.hasSons == "true" && currentNode.open == "true")
			{
				openNodes = openNodes.concat(currentNode.rid + MODEL_MULTI_VALUES_SEPERATOR);
			}
		}
		if (node.showSons == "true") node.showSons = "false";
		else node.showSons = "true";
		if (isResize) resizeAll();
		if (openNodes.indexOf(MODEL_MULTI_VALUES_SEPERATOR) != -1)
		{
			openNodes = openNodes.substring(0,openNodes.length - 1);
		} 
		nodeOpenEvent(modelId,openNodes,true);
		if (isOpenAction && !isRoot)
		{
			if(treeDiv.scrollTop > lastDirectSon.offsetTop)
			{
				document.all(lastDirectSon.id.substring(lastDirectSon.id.indexOf("row") + 3)).scrollIntoView();
			} 
			else if ((lastDirectSon.offsetTop + lastDirectSon.offsetHeight) > (treeDiv.clientHeight + treeDiv.scrollTop))
			{
				document.all(lastDirectSon.id.substring(lastDirectSon.id.indexOf("row") + 3)).scrollIntoView(false);
			} 
			if(treeDiv.scrollTop > node.offsetTop)
			{
				document.all(node.id.substring(node.id.indexOf(TREE_ROW_PREFIX) + 3)).scrollIntoView();
			} 
			else if ((node.offsetTop + node.offsetHeight) > (treeDiv.clientHeight + treeDiv.scrollTop))
			{
				document.all(node.id.substring(node.id.indexOf(TREE_ROW_PREFIX) + 3)).scrollIntoView(false);
			} 						
		}
		
	}
}

function getOpenNodesAsString(modelId)
{
	var openNodes = "";
	var nodes = document.all(modelId + TREE_TABLE_SUFFIX).rows;
	var length = nodes.length;
	for (index = 0;index < length;index++)
	{
		var node = nodes[index];
		if (node.open == "true")
		{
			openNodes = openNodes.concat(node.rid + MODEL_MULTI_VALUES_SEPERATOR); 
		}
	}
	if (openNodes.indexOf(MODEL_MULTI_VALUES_SEPERATOR) != -1)
	{
		openNodes = openNodes.substring(0,openNodes.length - 1);
	} 	
	return openNodes;
}

function getNodeLastDirectSon(node)
{
	var lastDirectSon;
	var level = new Number(node.level);
	var sibling = node.nextSibling;
	var tmp = "";
	while (sibling != null && sibling.level != level)
	{
		//tmp += sibling.id + "|";
		if (new Number(sibling.level) - 1 == level)
		{
			lastDirectSon = sibling;
		}
		sibling = sibling.nextSibling;
	} 
	return lastDirectSon;
	//alert(tmp);
}

function isParent(nodeId,parentNodeId)
{
	var idx = nodeId.indexOf(parentNodeId);
	if (idx != -1) 
	{
		if (nodeId.charAt(idx + parentNodeId.length) == SEPERATOR_MINUS) return true;
	}
	return false;
}

function getNodeParent(nodeId)
{
	var index = nodeId.lastIndexOf(SEPERATOR_MINUS);
	return(document.all(nodeId.substring(0,index)));
}

function getNodeParentId(nodeId)
{
	var index = nodeId.lastIndexOf(SEPERATOR_MINUS);
	return nodeId.substring(0,index);
}

function switchSigns(nodeSign,modelId)
{
	if (nodeSign.children(0).tagName == HTML_TAG_IMAGE) {
		var openImage = nodeSign.oImg != null ? nodeSign.oImg : getOpenNodeSign(modelId);
		var closeImage = nodeSign.cImg != null ? nodeSign.cImg : getClosedNodeSign(modelId);		
		var img = nodeSign.children(0);
		var index = img.src.lastIndexOf("/");
		var imgSrc = img.src.substring(index + 1);
		if (imgSrc == openImage) {
			img.src = img.src.substring(0,index + 1) + closeImage;	
		}
		else {
			img.src = img.src.substring(0,index + 1) + openImage;
		}
	}
	else {
		if (nodeSign.innerHTML == getOpenNodeSign(modelId)) {
			nodeSign.innerHTML = getClosedNodeSign(modelId);
		}
		else {
			nodeSign.innerHTML = getOpenNodeSign(modelId);	
		}
	}	
}

function checkAllTreeRows(modelId,check)
{
	var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
	var l = modelCheckboxes.length;
	for (index = 0;index < l;index++) {
		if (isDisplayed(modelCheckboxes[index].parentElement.parentElement)) {
			modelCheckboxes[index].checked = check;			
		}
	}
}

function getOpenNodeSign(modelId)
{
	return openNodeSign[modelId];
}
function getClosedNodeSign(modelId)
{
	return closeNodeSign[modelId];
}

/*selects tree node(only for tree)*/
function selectTreeNode(treeModelId,nodeDivId,selectedClassName)
{
	var previousNode = selectedNodesArray[treeModelId];
	if (previousNode != null)
	{
		var spaceIndex = previousNode.className.indexOf(" ");
		previousNode.className = previousNode.className.substring(0,spaceIndex);
	}
	var nodeDiv = document.getElementById(nodeDivId);
	selectedNodesArray[treeModelId] = nodeDiv;
	nodeDiv.className += " " + selectedClassName;
}

function getSelectedNodeExtraData(modelId,paramName)
{
	if (selectedNodesArray[modelId] != null)
	{
		var node = selectedNodesArray[modelId];
		return (getExtraData(node.ed,paramName));
	}
	else
	{
		return "";
	}
}

function getSelectedNodeLevel(modelId)
{
	if (selectedNodesArray[modelId] != null)
	{
		return selectedNodesArray[modelId].level;
	}
	else
	{
		return -1;
	}
}
