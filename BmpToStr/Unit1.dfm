object fmForm1: TfmForm1
  Left = 192
  Top = 124
  BorderStyle = bsDialog
  Caption = 'BMP to TXT'
  ClientHeight = 90
  ClientWidth = 133
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object btLoadImage: TButton
    Left = 16
    Top = 16
    Width = 75
    Height = 25
    Caption = 'Load image'
    TabOrder = 0
    OnClick = btLoadImage_onClick
  end
  object opdLoadImage: TOpenPictureDialog
    Left = 72
    Top = 32
  end
  object sdTextFile: TSaveDialog
    Left = 88
    Top = 48
  end
end
