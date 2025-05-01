using ChargedChat.Db;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VaultNet.Db
{
    public class Change
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ChangeId { get; set; }

        [Required]
        public int ProductId {  get; set; }

        [ForeignKey(nameof(ProductId))]
        public Product Product { get; set; }

        public required string Changes {  get; set; }

        public int UserId { get; set; }

        [ForeignKey(nameof(UserId))]
        public  User User { get; set; }

    }
}
