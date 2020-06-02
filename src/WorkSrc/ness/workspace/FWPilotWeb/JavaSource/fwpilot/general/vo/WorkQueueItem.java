package fwpilot.general.vo;

import java.util.Date;
import com.ness.fw.bl.ValueObject;

public interface WorkQueueItem extends ValueObject
{
	public static int TYPE_FROM_MANAGER = 1;
	public static int TYPE_PRODUCTION = 2;
	public static int TYPE_CHANGES = 3;
	public static int TYPE_APPROVALS = 4;
	public static int TYPE_REJECTED = 5;
	public static int TYPE_DOCUMENTS = 6;
	public static int TYPE_ALL = 7;

	public Date getEndDate();
	public Integer getId();
	public Integer getPriority();
	public Integer getProcessId();
	public Date getStartDate();
	public Integer getStatus();
	public Integer getStepId();
	public Integer getType();
	public void setEndDate(Date endDate);
	public void setPriority(Integer priority);
	public void setProcessId(Integer processId);
	public void setStartDate(Date startDate);
	public void setStatus(Integer status);
	public void setStepId(Integer stepId);
	public void setType(Integer type);
	public Integer getResponsibleUserId();
	public void setResponsibleUserId(Integer responsibleUserId);
	public Integer getAgentId();
	public Integer getCustomerId();
	public void setAgentId(Integer agentId);
	public void setCustomerId(Integer customerId);
}
