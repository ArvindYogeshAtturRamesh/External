CREATE TABLE [dbo].[Hotels] (
    [Id]       INT            IDENTITY (1, 1) NOT NULL,
    [Name]     NVARCHAR (MAX) NOT NULL,
    [Steet]    NVARCHAR (MAX) NOT NULL,
    [Postcode] NVARCHAR (MAX) NOT NULL,
    [State]    NVARCHAR (MAX) NOT NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

