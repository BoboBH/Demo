
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE id = object_id(N'[dbo].[string_split_first]') AND xtype IN (N'FN', N'IF', N'TF'))
   DROP FUNCTION dbo.string_split_first
GO
CREATE FUNCTION dbo.string_split_first (
	@String NVARCHAR(MAX),
	@Delimiter VARCHAR(MAX)
) RETURNS  NVARCHAR(MAX) AS
BEGIN
	DECLARE @result NVARCHAR(MAX)
	DECLARE @currentId INT = 1
	DECLARE @idx INT=1
	IF LEN(@String) < 1 OR LEN(ISNULL(@String,'')) = 0	
		SET @result =  ''
	ELSE
	BEGIN
		SET @idx = CHARINDEX(@Delimiter,@String)
		IF @idx != 0
			RETURN LEFT(@String,@idx-1)
		ELSE
			RETURN  @String
	END
	RETURN @result
END