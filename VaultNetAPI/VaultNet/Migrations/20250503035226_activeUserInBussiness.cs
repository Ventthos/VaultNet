using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace VaultNet.Migrations
{
    /// <inheritdoc />
    public partial class activeUserInBussiness : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Active",
                table: "UserBussiness",
                type: "tinyint(1)",
                nullable: false,
                defaultValue: false);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Active",
                table: "UserBussiness");
        }
    }
}
