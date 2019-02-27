
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE id = object_id(N'[dbo].[string_split]') AND xtype IN (N'FN', N'IF', N'TF'))
   DROP FUNCTION dbo.string_split
GO
CREATE FUNCTION dbo.string_split (
	@String NVARCHAR(MAX),
	@Delimiter VARCHAR(MAX)
) RETURNS @temptable TABLE (id int,val NVARCHAR(MAX)) AS
BEGIN
	DECLARE @currentId INT = 1
	DECLARE @idx INT=1
	DECLARE @slice NVARCHAR(MAX) 
	IF LEN(@String) < 1 OR LEN(ISNULL(@String,'')) = 0	
	BEGIN
		INSERT INTO @temptable(id, val) VALUES(@currentId, '')
		RETURN
	END
	WHILE @idx != 0
	BEGIN
		SET @idx = CHARINDEX(@Delimiter,@String)
		IF @idx != 0
			SET @slice = LEFT(@String,@idx - 1)
		ELSE
			SET @slice = @String
		INSERT INTO @temptable(id, val) VALUES(@currentId, @slice)
		SET @currentId = @currentId + 1
		SET @String = RIGHT (@String, LEN(@String) - @idx)
		IF LEN(@String) = 0
		BEGIN		
			INSERT INTO @temptable(id, val) VALUES(@currentId, '')
			BREAK
		END
	END
	RETURN
END

