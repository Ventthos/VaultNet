using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace VaultNet.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "BussinessUser");

            migrationBuilder.AddColumn<string>(
                name: "Image",
                table: "Users",
                type: "longtext",
                nullable: true)
                .Annotation("MySql:CharSet", "utf8mb4");

            migrationBuilder.AddColumn<int>(
                name: "BussinessId",
                table: "Units",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "BussinessId",
                table: "Categories",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "UserBussiness",
                columns: table => new
                {
                    UserId = table.Column<int>(type: "int", nullable: false),
                    BussinessId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserBussiness", x => new { x.UserId, x.BussinessId });
                    table.ForeignKey(
                        name: "FK_UserBussiness_Bussiness_BussinessId",
                        column: x => x.BussinessId,
                        principalTable: "Bussiness",
                        principalColumn: "BussinessId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_UserBussiness_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                })
                .Annotation("MySql:CharSet", "utf8mb4");

            migrationBuilder.CreateIndex(
                name: "IX_Units_BussinessId_Name",
                table: "Units",
                columns: new[] { "BussinessId", "Name" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_Categories_BussinessId_Name",
                table: "Categories",
                columns: new[] { "BussinessId", "Name" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_UserBussiness_BussinessId",
                table: "UserBussiness",
                column: "BussinessId");

            migrationBuilder.AddForeignKey(
                name: "FK_Categories_Bussiness_BussinessId",
                table: "Categories",
                column: "BussinessId",
                principalTable: "Bussiness",
                principalColumn: "BussinessId",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Units_Bussiness_BussinessId",
                table: "Units",
                column: "BussinessId",
                principalTable: "Bussiness",
                principalColumn: "BussinessId",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Categories_Bussiness_BussinessId",
                table: "Categories");

            migrationBuilder.DropForeignKey(
                name: "FK_Units_Bussiness_BussinessId",
                table: "Units");

            migrationBuilder.DropTable(
                name: "UserBussiness");

            migrationBuilder.DropIndex(
                name: "IX_Units_BussinessId_Name",
                table: "Units");

            migrationBuilder.DropIndex(
                name: "IX_Categories_BussinessId_Name",
                table: "Categories");

            migrationBuilder.DropColumn(
                name: "Image",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "BussinessId",
                table: "Units");

            migrationBuilder.DropColumn(
                name: "BussinessId",
                table: "Categories");

            migrationBuilder.CreateTable(
                name: "BussinessUser",
                columns: table => new
                {
                    BussinessId = table.Column<int>(type: "int", nullable: false),
                    UsersUserId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BussinessUser", x => new { x.BussinessId, x.UsersUserId });
                    table.ForeignKey(
                        name: "FK_BussinessUser_Bussiness_BussinessId",
                        column: x => x.BussinessId,
                        principalTable: "Bussiness",
                        principalColumn: "BussinessId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_BussinessUser_Users_UsersUserId",
                        column: x => x.UsersUserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                })
                .Annotation("MySql:CharSet", "utf8mb4");

            migrationBuilder.CreateIndex(
                name: "IX_BussinessUser_UsersUserId",
                table: "BussinessUser",
                column: "UsersUserId");
        }
    }
}
