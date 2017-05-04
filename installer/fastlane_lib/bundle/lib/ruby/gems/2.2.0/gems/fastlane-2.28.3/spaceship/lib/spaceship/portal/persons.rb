module Spaceship
  module Portal
    class Persons < PortalBase
      class << self
        def all
          members = client.team_members
          all_members = []
          member = factory_member(members["members"], "member")
          admins = factory_member(members["admins"], "admin")
          agent  = factory_member(members["agent"], "agent")

          all_members.concat(member)
          all_members.concat(admins)
          all_members << agent

          return all_members
        end

        def factory_member(members, type)
          if members.kind_of?(Hash)
            attrs = members
            attrs[:type] = type
            return Spaceship::Portal::Person.factory(attrs)
          end
          final_members = []
          members.each do |member|
            attrs = member
            attrs[:type] = type
            final_members << Spaceship::Portal::Person.factory(attrs)
          end
          return final_members
        end

        def find(email)
          all.each do |member|
            if member.email_address == email
              return member
            end
          end
          return nil
        end

        def invite(email, role)
          client.team_invite(email, role)
        end
      end
    end
  end
end
