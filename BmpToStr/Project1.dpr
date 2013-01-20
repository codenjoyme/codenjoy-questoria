program Project1;

uses
  Forms,
  Unit1 in 'Unit1.pas' {fmForm1};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TfmForm1, fmForm1);
  Application.Run;
end.
