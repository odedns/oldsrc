
/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :                                                       */
/*   Date         :                                                       */
/*   Description  :                                                       */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :                                                       */
/*   Date         :                                                       */
/*   Description  :                                                       */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/


#ifndef _PATH_H_
#define _PATH_H_

#ifdef __cplusplus
extern "C" {
#endif
	
#define IS_SEPARATOR(c)   (c == '/' || c == '\\')

/* split a drive letter from a path
 * return 0 if path contains a drive letter
 * return non-zero otherwise
 */
int path_split_drive(char *path, char *drive);

/* split a filename from a path
 * return 0 if path contains a filename 
 * return non-zero otherwise
 */
int path_split_file(char *path, char *file);

/* split a directory path from a path
 * return 0 if path contains a directory path
 * return non-zero otherwise
 */
int path_split_path(char *path, char *pname);

#ifdef __cplusplus
}
#endif
#endif
