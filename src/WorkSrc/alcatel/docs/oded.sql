select name, taskid, repeatsleft  from tb_Hsched_oded_task

delete from tb_Hsched_oded_task

select  *  from tb_Hsched_oded_task_info
delete from tb_Hsched_oded_task_info

SELECT  A.TASKID, A.TASK_CLASS_NAME , B.NAME FROM TB_HSCHED_TASK_INFO A, TB_HSCHED_TASK B WHERE A.TASKID=B.TASKID

DELETE FROM FROM TB_HSCHED_TASK_INFO A, TB_HSCHED_TASK B WHERE A.TASKID=B.TASKID 