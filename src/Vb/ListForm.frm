VERSION 4.00
Begin VB.Form ListForm 
   Caption         =   "ListBox"
   ClientHeight    =   1695
   ClientLeft      =   1905
   ClientTop       =   1560
   ClientWidth     =   4695
   Height          =   2100
   Left            =   1845
   LinkTopic       =   "Form2"
   ScaleHeight     =   1695
   ScaleWidth      =   4695
   Top             =   1215
   Width           =   4815
   Begin VB.CommandButton Command1 
      Caption         =   "OK"
      Height          =   375
      Left            =   840
      TabIndex        =   1
      Top             =   1200
      Width           =   1095
   End
   Begin VB.ListBox mylist 
      Height          =   645
      ItemData        =   "ListForm.frx":0000
      Left            =   600
      List            =   "ListForm.frx":0016
      TabIndex        =   0
      Top             =   240
      Width           =   3015
   End
End
Attribute VB_Name = "ListForm"
Attribute VB_Creatable = False
Attribute VB_Exposed = False
Private Sub Command1_Click()
    Unload Me
End Sub


