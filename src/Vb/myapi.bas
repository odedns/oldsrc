Attribute VB_Name = "Module1"
Option Explicit
Declare Function GetDiskFreeSpace Lib "kernel32" Alias "GetDiskFreeSpaceA" (ByVal lpRootPathName As String, lpSectorsPerCluster As Long, lpBytesPerSector As Long, lpNumberOfFreeClusters As Long, lpTtoalNumberOfClusters As Long) As Long


Public Sub myfree(my_drive As String, ByRef percent_used As Long, ByRef mb_total As Long, mb_free As Long)

Dim st As Long
Dim sectors_per_cluster As Long
Dim bytes_per_sector As Long
Dim free_clusters As Long
Dim total_clusters As Long
Dim total_space As Long
Dim free_space As Long
'Dim percent_used As Long


    Debug.Print "my_drive "; my_drive
    
    st = GetDiskFreeSpace("C:\", sectors_per_cluster, bytes_per_sector, free_clusters, total_clusters)
    'Debug.Print st
    'Debug.Print free_clusters
    'Debug.Print bytes_per_sector
    'Debug.Print sectors_per_cluster
    total_space = total_clusters * sectors_per_cluster * bytes_per_sector
    free_space = free_clusters * sectors_per_cluster * bytes_per_sector
    Debug.Print "*******************"
    
    Debug.Print "total space :"; total_space
    Debug.Print "free space : "; free_space
    mb_total = total_space / 1000000
    mb_free = free_space / 1000000
    
    percent_used = free_space / total_space * 100
    Debug.Print "percent :"; percent_used
    'Percent.Caption = Percent
    'Drive.Caption = "C"
    
    
    
    

End Sub


