using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using VaultNet.Db;

namespace ChargedChat.Db
{
    public class User
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int UserId { get; set; }

        [Required]
        [StringLength(100)]
        public required string Name { get; set; }

        [Required]
        [StringLength(100)]
        public required string PaternalLastName { get; set; }

        [StringLength(100)]
        public string? MaternalLastName { get; set; }

        [Required]
        public required string Email { get; set; }

        [Required]
        public required string Password { get; set; }

        public virtual ICollection<Bussiness> Bussiness { get; set; }

        public virtual ICollection<Change> Changes { get; set; }
    }
}
