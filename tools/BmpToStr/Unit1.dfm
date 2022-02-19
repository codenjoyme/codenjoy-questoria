object fmForm1: TfmForm1
  Left = 869
  Top = 172
  BorderStyle = bsDialog
  Caption = 'BMP to TXT'
  ClientHeight = 320
  ClientWidth = 303
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
  object edMask: TEdit
    Left = 16
    Top = 64
    Width = 265
    Height = 21
    TabOrder = 1
    Text = '# I@'
  end
  object debug: TMemo
    Left = 16
    Top = 104
    Width = 265
    Height = 201
    Lines.Strings = (
      'debug')
    TabOrder = 2
  end
  object opdLoadImage: TOpenPictureDialog
    Filter = 'Bitmaps (*.bmp)|*.bmp'
    Left = 80
    Top = 8
  end
  object sdTextFile: TSaveDialog
    Filter = 'Txt file|*.txt'
    Left = 96
    Top = 8
  end
end
