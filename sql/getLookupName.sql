
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE id = object_id(N'[dbo].[getLookupName]') AND xtype IN (N'FN', N'IF', N'TF'))
   DROP FUNCTION dbo.getLookupName
GO
CREATE FUNCTION dbo.getLookupName(@p_lookupTable VARCHAR(100),@p_ObjectId uniqueidentifier)
RETURNS NVARCHAR(MAX) AS
BEGIN
   DECLARE @r_looupName NVARCHAR(MAX)
   SET @r_looupName = NULL
   IF @p_lookupTable = 'new_reoisocountry' 
      SELECT @r_looupName = new_name FROM dbo.new_reoisocountryBase WHERE new_reoisocountryId = @p_ObjectId
   ELSE IF @p_lookupTable = 'new_aecode_settings' 
      SELECT @r_looupName = new_name FROM dbo.new_aecode_settingsBase WHERE new_aecode_settingsId = @p_ObjectId
   ELSE IF @p_lookupTable = 'systemuser' 
      SELECT @r_looupName = FullName FROM dbo.systemuserBase WHERE SystemUserId = @p_ObjectId
   RETURN @r_looupName
END
GO
GRANT EXECUTE ON dbo.getLookupName TO cdbdev;
