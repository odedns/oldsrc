include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#include </usr/include/linux/ext2_fs.h>

int main(int argc, char** argv)
{
	const int SuperSize=sizeof(struct ext2_super_block);
	const int DescSize=sizeof(struct ext2_group_desc);
	const int InodeSize=sizeof(struct ext2_inode);
	const int DirSize=sizeof(struct ext2_dir_entry_2);
	struct ext2_dir_entry_2 dir_entry;
	struct ext2_super_block super;
	struct ext2_group_desc groupDesc;
	int bitmapSize;
	struct ext2_inode root;
	int fd;
	unsigned int nodeCount, blockCount;
	if ((fd=open("/dev/sda1", O_RDONLY))<0)
	{
		printf("open fails\n");
		_exit(12);
	}

	if (lseek(fd, SuperSize, SEEK_SET)<0)
	{
		printf("seek fails\n");
		_exit(13);
	}

	if (read(fd, &super, SuperSize)!=SuperSize)
	{
		printf("read fails\n");
		_exit(14);
	}

	if (super.s_magic!=EXT2_SUPER_MAGIC)
	{
		printf("oh no!\n");
	}
	printf("node count=%d and block count=%d\n", super.s_inodes_count, super.s_blocks_count);
	printf("block per group=%d\n", super.s_blocks_per_group);
	printf("inodes per group=%d\n", super.s_inodes_per_group);
	printf("mount count=%d\n", super.s_mnt_count);
	printf("first inode=%d\n", super.s_first_ino);
	printf("inode size=%d\n", super.s_inode_size);
	printf("my block index=%d\n",super.s_block_group_nr);
	printf("volume name=%s\n", super.s_volume_name);
	printf("pre-allocate block number=%d\n", super.s_prealloc_blocks);
	printf("pre_allocate blocks for directory=%d\n", super.s_prealloc_dir_blocks);

	printf("first data block=%d\n", super.s_first_data_block);
	printf("block size=%d\n", super.s_log_block_size);
	printf("block size=%d\n", EXT2_BLOCK_SIZE(&super));

/*
	memset(&groupDesc, 255, DescSize);
	printf("block bitmap block index=%d\n", groupDesc.bg_block_bitmap);
	printf("inode bitmap block index=%d\n", groupDesc.bg_inode_bitmap);
	printf("inode table block index=%d\n", groupDesc.bg_inode_table);
	printf("free block count=%d\n", groupDesc.bg_free_blocks_count);
	printf("used dir count=%d\n", groupDesc.bg_used_dirs_count);
*/
	if (lseek(fd, 4096, SEEK_SET)<0)
	{
		printf("seek fails\n");
		_exit(13);
	}

	if (read(fd, &groupDesc, DescSize)!=DescSize)
	{
		printf("read group desc fails\n");
		_exit(18);
	}
	printf("block bitmap block index=%d\n", groupDesc.bg_block_bitmap);
	printf("inode bitmap block index=%d\n", groupDesc.bg_inode_bitmap);
	printf("inode table block index=%d\n", groupDesc.bg_inode_table);
	printf("free block count=%d\n", groupDesc.bg_free_blocks_count);
	printf("free INODE count=%d\n", groupDesc.bg_free_inodes_count);
	printf("pad=%d\n", groupDesc.bg_pad);

	printf("used dir count=%d\n", groupDesc.bg_used_dirs_count);

	//bitmapSize=EXT2_BLOCK_SIZE(&super);

	printf("go to inode table block %d\n", groupDesc.bg_inode_table);
	if (lseek(fd, 4096*groupDesc.bg_inode_table, SEEK_SET)<0)
	//if (lseek(fd, 4096*5, SEEK_SET)<0)
	{
		printf("seek fail again\n");
		_exit(20);
	}
	if (lseek(fd, InodeSize*(EXT2_ROOT_INO-1), SEEK_CUR)<0)
	//if (lseek(fd, InodeSize*(super.s_first_ino+1), SEEK_CUR)<0)
	{
		printf("what\n");
		_exit(23);
	}
	if (read(fd, &root, InodeSize)!=InodeSize)
	{
		printf("what\n");
		_exit(23);
	}
	printf("size=%d, this inode has block number=%d\n", root.i_size, root.i_blocks);		
	printf("block numberof first block=%d\n", root.i_block[0]);
	printf("imode=%d\n", root.i_mode);
	printf("link count=%d, access time %d, modified time %d\n", root.i_links_count, root.i_atime, root.i_mtime); 

	int i;
	for (i=0; i<root.i_blocks; i++)
	{
		printf("the pointer of block %d =%d\n", i, root.i_block[i]);
	}
	printf("the root directory's data block is %d and let's go there\n", root.i_block[0]);
	if (lseek(fd, 4096*root.i_block[0], SEEK_SET)<0)
	{
		printf("what\n");
		_exit(23);
	}
	int counter=0;
	unsigned int inode;
	unsigned short rec_len;
	unsigned char name_len;
	unsigned char file_type;
	char buffer[255];
	int offset;
	do
	{
		read(fd, &inode, 4);
		printf("inode number %d\n",inode);
		read(fd, &rec_len, 2);
		printf("inode reclength %d\n",rec_len);
		read(fd, &name_len, 1);
		printf("name len=%d\n", name_len);
		read(fd, &file_type, 1);
		read(fd, buffer, name_len);
		buffer[name_len]='\0';
		printf("name is %s\n", buffer);
		offset=rec_len-4-2-1-1-name_len;
		lseek(fd, offset, SEEK_CUR);
		switch(file_type)
		{
		case EXT2_FT_UNKNOWN:
			printf("unknown\n");
			break;
		case EXT2_FT_REG_FILE:
			printf("regular\n");
			break;
		case EXT2_FT_DIR:
			printf("directory\n");
			break;
		case EXT2_FT_CHRDEV:
			printf("chardev\n");
			break;
		case EXT2_FT_BLKDEV:
			printf("blk\n");
			break;
		case EXT2_FT_FIFO:
			printf("fifo\n");
			break;
		case EXT2_FT_SOCK:
			printf("socket\n");
			break;
		case EXT2_FT_SYMLINK:
			printf("symbolic link\n");
			break;
		case EXT2_FT_MAX:
			printf("max\n");
			break;
		}
		counter++;
	}
	//while (dir_entry.rec_len!=3999);
	while (rec_len!=4096);
	//while (counter<40);

/*
	if (lseek(fd, 2*SuperSize+DescSize+4096*2+(EXT2_ROOT_INO-1)*InodeSize, SEEK_SET)<0)
	{
		printf("what?\n");
		_exit(34);
	}
	read(fd, &root, InodeSize);
	printf("isize=%d\niblocks=%d\n", root.i_size, root.i_blocks);

*/
	
	
	
	return 0;
}